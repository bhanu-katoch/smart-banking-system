package com.bankingsystem.service;

import com.bankingsystem.dao.AccountDAO;
import com.bankingsystem.model.Account;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public void createAccount(Account account) {
        accountDAO.save(account);
    }

    public double checkBalance(int userId) {
        Account account = accountDAO.findByUserId(userId);
        return account != null ? account.getBalance() : 0.0;
    }

    // Create account only if user doesn't already have one. Returns the created
    // Account or null if an account already exists for the user.
    public Account createAccountIfNone(int userId, String accountNumber, double initialBalance) {
        Account existing = accountDAO.findByUserId(userId);
        if (existing != null) {
            return null; // account already exists for this user
        }
        Account account = new Account(0, userId, accountNumber, initialBalance);
        accountDAO.save(account);
        return account;
    }

    // Return the account for a given userId (or null)
    public Account getAccountByUserId(int userId) {
        return accountDAO.findByUserId(userId);
    }

    // Check if an account exists by account number
    public boolean accountExists(String accountNumber) {
        return accountDAO.findByAccountNumber(accountNumber) != null;
    }
}
