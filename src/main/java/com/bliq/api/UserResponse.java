package com.bliq.api;

public class UserResponse {
    public String message;
    public String status;
    public String payload;

    public UserResponse(String message, String status) {
        this.message = message;
        this.status = status;

    }

    public UserResponse(String message, String status, String payload) {
        this.message = message;
        this.status = status;
        this.payload = payload;
    }
}
