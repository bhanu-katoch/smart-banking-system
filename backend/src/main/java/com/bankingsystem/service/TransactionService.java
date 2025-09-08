package com.bankingsystem.service;

import com.bankingsystem.dao.TransactionDAO;

public class TransactionService {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void deposit(int accountId, double amount) {
        transactionDAO.recordTransaction(accountId, "deposit", amount);
    }

    public void withdraw(int accountId, double amount) {
        transactionDAO.recordTransaction(accountId, "withdraw", amount);
    }
}
