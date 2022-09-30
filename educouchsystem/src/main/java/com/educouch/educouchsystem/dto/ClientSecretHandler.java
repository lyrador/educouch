package com.educouch.educouchsystem.dto;

public class ClientSecretHandler {
    String clientSecret;

    public ClientSecretHandler(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
