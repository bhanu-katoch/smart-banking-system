package com.bankingsystem.service;

import com.bankingsystem.dao.TransactionDAO;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public boolean deposit(int accountId, double amount, String note) {
        return transactionDAO.deposit(accountId, amount, note);
    }

    public boolean withdraw(int accountId, double amount, String note) {
        return transactionDAO.withdraw(accountId, amount, note);
    }

    public java.util.List<com.bankingsystem.model.TransactionRecord> getTransactionHistory(int accountId) {
        return transactionDAO.getTransactionHistory(accountId);
    }
}
