package com.bankingsystem.model;

public class Student extends User {

    public Student() {
    }

    private String college;

    public Student(String name, String email, String passwordHash, String college) {
        super(name, email, passwordHash);
        this.college = college;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @Override
    public String getUserType() {
        return "Student";
    }
}
