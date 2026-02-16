package com.medical.app.exams.application.services.contracts;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthTokenServiceContract {
    TokenResponse generateToken(String clientId, String clientSecret);

    boolean validateToken(String token);

    Optional<String> getValidToken(String clientId);

    int cleanupExpiredTokens();

    record TokenResponse(String token, String clientId, LocalDateTime expiresAt) {}

}
