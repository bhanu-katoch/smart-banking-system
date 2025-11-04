package com.bankingsystem.dao;

import com.bankingsystem.config.DatabaseConfig;
import com.bankingsystem.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {

    // Ensure accounts table exists and insert the provided account.
    public void save(Account account) {
        // Ensure DB schema exists (creates users/accounts/transactions if missing)
        DatabaseConfig.init();

        String insertSql = "INSERT INTO accounts(userId, accountNumber, balance) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            // enable foreign keys for this connection
            stmt.execute("PRAGMA foreign_keys = ON;");

            // insert account
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, account.getUserId());
                pstmt.setString(2, account.getAccountNumber());
                pstmt.setDouble(3, account.getBalance());

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    ResultSet rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        account.setId(rs.getInt(1));
                    }
                    System.out.println("Account created successfully for userId=" + account.getUserId());
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while saving account.");
            e.printStackTrace();
        }
    }

    // Find account by userId (returns first account found or null)
    public Account findByUserId(int userId) {
        // Ensure DB schema exists (creates users/accounts/transactions if missing)
        DatabaseConfig.init();

        String selectSql = "SELECT id, userId, accountNumber, balance FROM accounts WHERE userId = ? LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            // enable foreign keys for this connection
            stmt.execute("PRAGMA foreign_keys = ON;");

            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String acctNum = rs.getString("accountNumber");
                    double balance = rs.getDouble("balance");

                    return new Account(id, userId, acctNum, balance);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching account.");
            e.printStackTrace();
        }

        return null;
    }

    // Find account by account number
    public Account findByAccountNumber(String accountNumber) {
        String selectSql = "SELECT id, userId, accountNumber, balance FROM accounts WHERE accountNumber = ? LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement()) {

            // enable foreign keys for this connection
            stmt.execute("PRAGMA foreign_keys = ON;");

            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setString(1, accountNumber);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("id");
                    int uid = rs.getInt("userId");
                    String acctNum = rs.getString("accountNumber");
                    double balance = rs.getDouble("balance");

                    return new Account(id, uid, acctNum, balance);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching account by accountNumber.");
            e.printStackTrace();
        }

        return null;
    }
}
