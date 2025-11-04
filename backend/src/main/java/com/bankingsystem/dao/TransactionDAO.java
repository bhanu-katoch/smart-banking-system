package com.bankingsystem.dao;

import com.bankingsystem.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAO {

    // Deposit amount into accountId. Returns true on success.
    public boolean deposit(int accountId, double amount, String note) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        // Ensure DB schema exists
        DatabaseConfig.init();

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();

            // enable foreign keys for this connection
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            // read current balance
            String selectSql = "SELECT balance FROM accounts WHERE id = ?";
            double current;
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, accountId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        System.out.println("Account not found: " + accountId);
                        return false;
                    }
                    current = rs.getDouble("balance");
                }
            }

            double newBalance = current + amount;

            // update balance
            String updateSql = "UPDATE accounts SET balance = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setDouble(1, newBalance);
                ps.setInt(2, accountId);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    System.out.println("Failed to update account balance for id=" + accountId);
                    return false;
                }
            }

            // record transaction (include note)
            if (!recordTransaction(conn, accountId, "DEPOSIT", amount, note)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("Deposit successful for accountId=" + accountId + ", amount=" + amount);
            return true;

        } catch (SQLException e) {
            System.out.println("Error during deposit for accountId=" + accountId);
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Withdraw amount from accountId. Returns true on success (sufficient funds).
    public boolean withdraw(int accountId, double amount, String note) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return false;
        }

        // Ensure DB schema exists
        DatabaseConfig.init();

        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();

            // enable foreign keys for this connection
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }

            conn.setAutoCommit(false);

            // read current balance
            String selectSql = "SELECT balance FROM accounts WHERE id = ?";
            double current;
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, accountId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        System.out.println("Account not found: " + accountId);
                        return false;
                    }
                    current = rs.getDouble("balance");
                }
            }

            if (current < amount) {
                System.out.println("Insufficient funds for accountId=" + accountId);
                try {
                    conn.rollback();
                } catch (SQLException ignore) {
                }
                return false;
            }

            double newBalance = current - amount;

            // update balance
            String updateSql = "UPDATE accounts SET balance = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setDouble(1, newBalance);
                ps.setInt(2, accountId);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    System.out.println("Failed to update account balance for id=" + accountId);
                    return false;
                }
            }

            // record transaction (include note)
            if (!recordTransaction(conn, accountId, "WITHDRAWAL", amount, note)) {
                conn.rollback();
                return false;
            }

            conn.commit();
            System.out.println("Withdrawal successful for accountId=" + accountId + ", amount=" + amount);
            return true;

        } catch (SQLException e) {
            System.out.println("Error during withdrawal for accountId=" + accountId);
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Helper to record a transaction using an existing connection. Connection must
    // be open.
    private boolean recordTransaction(Connection conn, int accountId, String type, double amount, String note)
            throws SQLException {
        String insertSql = "INSERT INTO transactions(accountId, type, amount, note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, note == null ? "" : note);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    // Get transaction history for an account as structured TransactionRecord
    // objects
    public java.util.List<com.bankingsystem.model.TransactionRecord> getTransactionHistory(int accountId) {
        java.util.List<com.bankingsystem.model.TransactionRecord> out = new java.util.ArrayList<>();
        DatabaseConfig.init();
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT id, type, amount, note, timestamp FROM transactions WHERE accountId = ? ORDER BY id DESC")) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    com.bankingsystem.model.TransactionRecord tr = new com.bankingsystem.model.TransactionRecord(
                            rs.getInt("id"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getString("note"),
                            rs.getString("timestamp"));
                    out.add(tr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return out;
    }
}
