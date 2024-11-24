package com.bliq.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    private Long id;  // Primary key with auto-increment

    @Column(name = "user_id", nullable = false)  // Specify column name
    private Long userId;

    @Column(name = "session_token", nullable = false)  // Set unique constraint on mail
    private String sessionToken;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive; // Changed to standard boolean naming

    @Column(name = "device")
    private String device;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
