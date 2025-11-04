// package com.bankingsystem.config;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;

// public class DatabaseConfig {
//     private static final String URL = "jdbc:postgresql://localhost:5432/bankdb";
//     private static final String USER = "postgres";
//     private static final String PASSWORD = "password";

//     public static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(URL, USER, PASSWORD);
//     }
// }
package com.bankingsystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
        private static final String URL = "jdbc:sqlite:database.db"; // SQLite database file path
        // Ensure init() only runs once per JVM run to avoid noisy messages
        private static boolean initialized = false;

        /**
         * Returns a connection to the SQLite database.
         * 
         * @return Connection object
         * @throws SQLException if connection fails
         */
        public static Connection getConnection() throws SQLException {
                return DriverManager.getConnection(URL);
        }

        public static void init() {
                // Avoid re-running initialization (and printing) on every DAO call
                if (initialized) {
                        return;
                }

                try (Connection conn = DatabaseConfig.getConnection();
                                Statement stmt = conn.createStatement()) {

                        // Enable foreign key enforcement in SQLite
                        stmt.execute("PRAGMA foreign_keys = ON;");

                        // Create users table (no institute column - specific details stored in
                        // child tables)
                        String usersSql = "CREATE TABLE IF NOT EXISTS users (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "name TEXT NOT NULL," +
                                        "email TEXT UNIQUE NOT NULL," +
                                        "passwordHash TEXT NOT NULL," +
                                        "userType TEXT NOT NULL" +
                                        ");";
                        stmt.execute(usersSql);

                        // Create accounts table
                        String accountsSql = "CREATE TABLE IF NOT EXISTS accounts (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "userId INTEGER NOT NULL," +
                                        "accountNumber TEXT NOT NULL," +
                                        "balance REAL NOT NULL," +
                                        "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                                        ");";
                        stmt.execute(accountsSql);

                        // Create transactions table with extra columns for per-operation notes
                        String transactionsSql = "CREATE TABLE IF NOT EXISTS transactions (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "accountId INTEGER NOT NULL," +
                                        "type TEXT NOT NULL," +
                                        "amount REAL NOT NULL," +
                                        "note TEXT DEFAULT ''," +
                                        "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                                        "FOREIGN KEY(accountId) REFERENCES accounts(id) ON DELETE CASCADE" +
                                        ");";
                        stmt.execute(transactionsSql);

                        // Create student/professional/customer tables which reference users
                        String studentsSql = "CREATE TABLE IF NOT EXISTS students (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "userId INTEGER UNIQUE NOT NULL," +
                                        "college TEXT NOT NULL," +
                                        "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                                        ");";
                        stmt.execute(studentsSql);

                        String professionalsSql = "CREATE TABLE IF NOT EXISTS professionals (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "userId INTEGER UNIQUE NOT NULL," +
                                        "company TEXT NOT NULL," +
                                        "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                                        ");";
                        stmt.execute(professionalsSql);

                        String customersSql = "CREATE TABLE IF NOT EXISTS customers (" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                        "userId INTEGER UNIQUE NOT NULL," +
                                        "address TEXT," +
                                        "phone TEXT," +
                                        "FOREIGN KEY(userId) REFERENCES users(id) ON DELETE CASCADE" +
                                        ");";
                        stmt.execute(customersSql);

                        System.out.println("Database initialized successfully.");
                        initialized = true;

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}
