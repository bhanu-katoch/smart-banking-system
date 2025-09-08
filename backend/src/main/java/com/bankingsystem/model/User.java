package com.bankingsystem.model;

public abstract class User {
    protected String name;
    protected String email;
    protected String passwordHash;

    // Default constructor (needed for JDBC mapping)
    public User() {}

    // Parameterized constructor
    public User( String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Abstract method to get user type
    public abstract String getUserType();

    // Getters and setters
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
