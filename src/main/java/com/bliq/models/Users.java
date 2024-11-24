package com.bliq.models;


import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    private Long id;  // Primary key with auto-increment

    @Column(name = "name", nullable = false)  // Specify column name
    private String name;

    @Column(name = "mail", unique = true, nullable = false)  // Set unique constraint on mail
    private String mail;

    @Column(name = "status")
    private int status;

    @Column(name = "is_admin")
    private boolean isAdmin; // Changed to standard boolean naming

    @Column(name = "paswd_hash", nullable = false)
    private String paswdHash;  // Standardize the field name to paswdHash

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPaswdHash() {
        return paswdHash;
    }

    public void setPaswdHash(String paswdHash) {
        this.paswdHash = paswdHash;
    }
}
