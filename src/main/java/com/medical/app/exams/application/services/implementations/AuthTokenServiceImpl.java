package com.medical.app.exams.application.services.implementations;

import com.medical.app.exams.application.services.contracts.AuthTokenServiceContract;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import javax.crypto.SecretKey;

@Service
public class AuthTokenServiceImpl implements AuthTokenServiceContract {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenServiceImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final SecretKey signingKey;
    private final Map<String, String> clientCredentials = new HashMap<>();

    public AuthTokenServiceImpl(
            JdbcTemplate jdbcTemplate,
            @Value("${JWT_SECRET}") String jwtSecret,
            @Value("${CLIENT_ID_1}") String clientId1,
            @Value("${SECRET_1}") String secret1) {

        this.jdbcTemplate = jdbcTemplate;

        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT_SECRET should have 32+ characters");
        }
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        if (clientId1 == null || secret1 == null) {
            throw new IllegalArgumentException("CLIENT_ID_1 and SECRET_1 must be set in environment");
        }
        clientCredentials.put(clientId1, secret1);
    }

    @Override
    @Transactional
    public TokenResponse generateToken(String clientId, String clientSecret) {
        logger.info("Starting token generation for client_id={}", clientId);

        String storedSecret = clientCredentials.get(clientId);
        if (storedSecret == null || !storedSecret.equals(clientSecret)) {
            logger.warn("Invalid credentials for client_id={}", clientId);
            throw new IllegalArgumentException("invalid client_id or secret");
        }

        LocalDateTime expiresAt = LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(2);
        Date expirationDate = Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant());

        Map<String, Object> payload = new HashMap<>();
        payload.put("client_id", clientId);
        payload.put("exp", expirationDate);

        String tokenString;
        try {
            JwtBuilder builder = Jwts.builder();
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
            tokenString = builder
                    .signWith(signingKey)
                    .compact();
            logger.info("JWT token successfully generated for client_id={}", clientId);
        } catch (Exception e) {
            logger.error("Failed to sign JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("error generating token: " + e.getMessage());
        }

        String sql = "INSERT INTO public.auth_tokens (id, client_id, jwt_token, expires_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
        UUID id = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));

        try {
            int rows = jdbcTemplate.update(sql,
                    id.toString(),
                    clientId,
                    tokenString,
                    expiresAt,
                    now,
                    now
            );
            if (rows != 1) {
                throw new RuntimeException("Failed to insert token into database");
            }
            logger.info("Token successfully saved in database for client_id={}", clientId);
        } catch (org.springframework.dao.DataAccessException e) {
            logger.error("Failed to save token to database: {}", e.getMessage());
            throw new IllegalArgumentException("error to save on database: " + e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Unexpected error while saving token to database: {}", e.getMessage());
            throw new IllegalArgumentException("unexpected error to save on database: " + e.getMessage());
        }

        return new TokenResponse(tokenString, clientId, expiresAt);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM public.auth_tokens WHERE jwt_token = ? AND expires_at > NOW())";
            Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, token);
            if (exists == null || !exists) {
                return false;
            }
            Jwts.parser()
                    .verifyWith(signingKey) 
                    .build()
                    .parseClaimsJws(token.toString());
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Token expired: {}", e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Invalid token: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Error validating token: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<String> getValidToken(String clientId) {
        try {
            String sql = "SELECT jwt_token FROM public.auth_tokens WHERE client_id = ? AND expires_at > NOW() ORDER BY created_at DESC LIMIT 1";
            List<String> tokens = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("jwt_token"), clientId);
            return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
        } catch (Exception e) {
            logger.error("Error while fetching valid token: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public int cleanupExpiredTokens() {
        try {
            String sql = "DELETE FROM public.auth_tokens WHERE expires_at <= NOW()";
            int deleted = jdbcTemplate.update(sql);
            if (deleted > 0) {
                logger.info("Expired tokens cleanup: {} tokens removed", deleted);
            }
            return deleted;
        } catch (Exception e) {
            logger.error("Error cleaning up expired tokens: {}", e.getMessage());
            return 0;
        }
    }
}