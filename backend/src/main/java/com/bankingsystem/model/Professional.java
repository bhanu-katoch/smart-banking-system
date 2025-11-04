package com.bankingsystem.model;

public class Professional extends User {

    public Professional() {
    }

    private String company;

    public Professional(String name, String email, String passwordHash, String company) {
        super(name, email, passwordHash);
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String getUserType() {
        return "Professional";
    }
}
