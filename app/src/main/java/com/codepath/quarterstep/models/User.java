package com.codepath.quarterstep.models;

public class User {
    private String username;
    private String fname;
    private String lname;
    private String email;
    private String createdAt;
    private String userId;

    public User() {
        // must have a public no-argument constructor for firebase
    }

    public User(String username, String fname, String lname, String email, String createdAt, String userId) {
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
