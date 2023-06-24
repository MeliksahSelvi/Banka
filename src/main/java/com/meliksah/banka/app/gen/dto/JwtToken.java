package com.meliksah.banka.app.gen.dto;

/**
 * @Author mselvi
 * @Created 24.06.2023
 */

public class JwtToken {

    private Long userId;
    private String token;
    private Long tokenIssuedTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTokenIssuedTime() {
        return tokenIssuedTime;
    }

    public void setTokenIssuedTime(Long tokenIssuedTime) {
        this.tokenIssuedTime = tokenIssuedTime;
    }
}
