package com.deliverytech.delivery_api.data.response;

public class LoginDTOResponse {
    private String token;
    private String type = "Bearer";
    private Long expiration;
    private UserDTOResponse user;

    // Constructors
    public LoginDTOResponse() {}

    public LoginDTOResponse(String token, Long expiration, UserDTOResponse user) {
        this.token = token;
        this.expiration = expiration;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getExpiration() { return expiration; }
    public void setExpiration(Long expiration) { this.expiration = expiration; }

    public UserDTOResponse getUser() { return user; }
    public void setUser(UserDTOResponse user) { this.user = user; }
}