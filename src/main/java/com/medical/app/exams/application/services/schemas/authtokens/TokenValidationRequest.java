package com.medical.app.exams.application.services.schemas.authtokens;

import jakarta.validation.constraints.NotBlank;

public record TokenValidationRequest(
        @NotBlank(message = "token is required")
        String token
) {}
