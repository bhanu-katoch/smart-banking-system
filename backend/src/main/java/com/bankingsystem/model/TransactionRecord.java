package com.bankingsystem.model;

public class TransactionRecord {
    private int id;
    private String type;
    private double amount;
    private String note;
    private String timestamp;

    public TransactionRecord(int id, String type, double amount, String note, String timestamp) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.note = note;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
