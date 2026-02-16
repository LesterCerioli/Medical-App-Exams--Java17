package com.medical.app.exams.web.controller;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.medical.app.exams.application.services.contracts.AuthTokenServiceContract;
import com.medical.app.exams.application.services.schemas.authtokens.AuthTokenRequest;
import com.medical.app.exams.application.services.schemas.authtokens.TokenValidationResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/auth")
public class AuthTokenController {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenController.class);

    private final AuthTokenServiceContract authTokenService;
    private final SecretKey signingKey;

    public AuthTokenController(
            AuthTokenServiceContract authTokenService,
            @Value("${JWT_SECRET}") String jwtSecret
    ) {
        this.authTokenService = authTokenService;
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT_SECRET must have at least 32 characters");
        }
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a new JWT token for a client.
     *
     * @param request Client credentials (client_id, client_secret)
     * @return TokenResponse containing the token, client_id, and expiration
     */
    @PostMapping("/token")
    public AuthTokenServiceContract.TokenResponse generateToken(@RequestBody AuthTokenRequest request) {
        logger.info("POST /auth/token called for client_id={}", request.getClientId());

        if (request.getClientId() == null || request.getClientSecret() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Both 'client_id' and 'client_secret' are required");
        }

        try {
            return authTokenService.generateToken(request.getClientId(), request.getClientSecret());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error generating token", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Token generation failed");
        }
    }

    /**
     * Validate a JWT token provided in the Authorization header.
     *
     * @param authorization Header value (Bearer <token>)
     * @return Validation result with client_id if valid
     */
    @PostMapping("/validate")
    public TokenValidationResponse validateToken(@RequestHeader("Authorization") String authorization) {
        logger.info("POST /auth/validate called");

        String token = extractTokenFromHeader(authorization);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Authorization header format");
        }
        
        if (!authTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
        
        String clientId;
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            clientId = claims.get("client_id", String.class);
        } catch (JwtException e) {
            logger.warn("Failed to decode token after DB validation: {}", e.getMessage());
            
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token validation failed");
        }

        return new TokenValidationResponse(true, "Token is valid");
    }

    /**
     * Get a valid token for a client (if any). Public endpoint, no authentication required.
     *
     * @param clientId Client identifier
     * @return The valid token
     */
    @GetMapping("/token/{clientId}")
    public Map<String, String> getValidToken(@PathVariable String clientId) {
        logger.info("GET /auth/token/{} called", clientId);

        Optional<String> tokenOpt = authTokenService.getValidToken(clientId);
        if (tokenOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No valid token found");
        }
        return Map.of("token", tokenOpt.get());
    }

    /**
     * Clean up expired tokens. Requires a valid token in Authorization header.
     *
     * @param authorization Header value (Bearer <token>)
     * @return Number of deleted tokens
     */
    @DeleteMapping("/cleanup")
    public Map<String, String> cleanupExpiredTokens(@RequestHeader("Authorization") String authorization) {
        logger.info("DELETE /auth/cleanup called");

        
        String token = extractTokenFromHeader(authorization);
        if (token == null || !authTokenService.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        int deleted = authTokenService.cleanupExpiredTokens();
        return Map.of("message", "Cleaned up " + deleted + " expired tokens");
    }
    
    private String extractTokenFromHeader(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }
        return authorization.substring(7);
    }
}