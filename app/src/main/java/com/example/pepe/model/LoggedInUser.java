package com.example.pepe.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String id;
    private String username;
    private String password;
    private Integer caffeine;

    public LoggedInUser(String id_, String username_, String password_, Integer caffeine_) {
        this.id = id_;
        this.username = username_;
        this.password = password_;
        this.caffeine = caffeine_;
    }

// getters and setters for each field
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCaffeine() {
        return caffeine;
    }
    public void setCaffeine(Integer caffeine) {
        this.caffeine = caffeine;
    }
}