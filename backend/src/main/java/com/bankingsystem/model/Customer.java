package com.bankingsystem.model;

public class Customer extends User {

    public Customer() {
    }

    public Customer(String name, String email, String passwordHash) {
        super(name, email, passwordHash);
    }

    private String address;
    private String phone;

    public Customer(String name, String email, String passwordHash, String address, String phone) {
        super(name, email, passwordHash);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getUserType() {
        return "Customer";
    }
}
