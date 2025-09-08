package com.bankingsystem.model;

public class Student extends User {
    private String college;

    public Student(){}
    public Student( String name, String email, String passwordHash, String college) {
        super( name, email, passwordHash);
        this.college = college;
    }

    @Override
    public String getUserType() {
        return "Student";
    }
}
