package com.medical.app.exams.application.services.schemas.authtokens;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class AuthTokenRequest {
    @NotBlank
    @JsonProperty("client_id")
    private String clientId;

    @NotBlank
    @JsonProperty("client_secret")
    private String clientSecret;
    
    public AuthTokenRequest() {}

    public AuthTokenRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
}