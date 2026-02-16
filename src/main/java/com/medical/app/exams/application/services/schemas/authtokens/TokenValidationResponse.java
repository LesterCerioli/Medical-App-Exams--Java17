package com.medical.app.exams.application.services.schemas.authtokens;

public record TokenValidationResponse(
        boolean valid,
        String message
) {}