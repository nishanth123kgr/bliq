package com.bliq.models;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "chat_actions")
public class ChatActions {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "action")
    private Long action;

    @Column(name = "done_at")
    private Date doneAt;

    @Column(name = "done_by")
    private Long doneBy;

    @PrePersist
    protected void onCreate() {
        if (this.doneAt == null) {
            this.doneAt = new Date();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAction() {
        return action;
    }

    public void setAction(Long action) {
        this.action = action;
    }

    public Date getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    public Long getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(Long doneBy) {
        this.doneBy = doneBy;
    }
}
