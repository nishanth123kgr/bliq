package com.bliq.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "participants")
public class Participants {
    @Id
    @Column(name = "chat_id")  // Specify column name
    private Long chatId;

    @Id
    @Column(name = "user_id", nullable = false)  // Set unique constraint on mail
    private Long userId;

    @Column(name = "is_gp_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "joined_at", nullable = false)
    private Date joinedAt;

    @Column(name = "last_seen")
    private Date lastSeen;

    @Column(name = "added_by")
    private Long addedBy;

    @PrePersist
    protected void onCreate() {
        if (this.joinedAt == null) {
            this.joinedAt = new Date();
        }
    }

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
