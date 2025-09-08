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
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create users table
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "name TEXT NOT NULL," +
                         "email TEXT UNIQUE NOT NULL," +
                         "passwordHash TEXT NOT NULL," +
                         "userType TEXT NOT NULL" +
                         ");";

            stmt.execute(sql);

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
