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
}
