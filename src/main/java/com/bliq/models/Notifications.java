package com.bliq.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    private Long id;

    @Column(name = "req_id")
    private Long reqId;

    @Column(name = "msg_id")
    private Long msgId;

    @Column(name = "sent_at", nullable = false)
    private Date sentAt;

    @Column(name = "type", nullable = false)
    private Long type;

    @PrePersist
    protected void onCreate() {
        if (this.sentAt == null) {
            this.sentAt = new Date();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReqId() {
        return reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @PreUpdate
    protected void checkReqMsgId() {
        if (this.reqId == null && this.msgId == null) {
            throw new IllegalArgumentException("Either req_id or msg_id must be set");
        }
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "id=" + id +
                ", reqId=" + reqId +
                ", msgId=" + msgId +
                ", sentAt=" + sentAt +
                ", type=" + type +
                '}';
    }


}



