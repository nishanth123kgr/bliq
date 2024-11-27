package com.bliq.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "join_requests")
public class JoinRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "status", nullable = false)
    private Long status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "requested_at", nullable = false)
    private Date requestedAt;

    public JoinRequests() {

    }

    @PrePersist
    protected void onCreate() {
        if (this.requestedAt == null) {
            this.requestedAt = new Date();
        }
        if(this.status == null) {
            this.status = 0L;
        }
    }

    public JoinRequests(String userId, String chatId) {
        this.userId = Long.parseLong(userId);
        this.chatId = Long.parseLong(chatId);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
