package com.medical.app.exams.application.services.schemas.authtokens;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthTokenResponse(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("expires_in") Long expiresIn
) {
    
    public AuthTokenResponse {
        if (tokenType == null) tokenType = "Bearer";
    }
}
