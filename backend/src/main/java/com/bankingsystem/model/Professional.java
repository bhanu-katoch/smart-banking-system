package com.bankingsystem.model;

public class Professional extends User {
    private String company;

    public Professional() {
    }

    public Professional(String name, String email, String passwordHash, String company) {
        super(name, email, passwordHash);
        this.company = company;
    }

    @Override
    public String getUserType() {
        return "Professional";
    }
}
