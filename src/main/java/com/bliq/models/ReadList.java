package com.bliq.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "read_list")
public class ReadList {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "read_by")
    private Long readBy;

    @Column(name = "read_at")
    private Date readAt;

    @PrePersist
    protected void onCreate() {
        if (this.readAt == null) {
            this.readAt = new Date();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getReadBy() {
        return readBy;
    }

    public void setReadBy(Long readBy) {
        this.readBy = readBy;
    }

    public Date getReadAt() {
        return readAt;
    }

    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
}
