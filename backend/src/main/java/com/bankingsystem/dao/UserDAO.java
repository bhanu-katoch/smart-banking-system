package com.bankingsystem.dao;

import com.bankingsystem.config.DatabaseConfig;
import com.bankingsystem.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Register/save a new user
    public void save(User user) {
        String sql = "INSERT INTO users(name, email, passwordHash, userType) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getUserType());

            int rows = pstmt.executeUpdate();
            if (rows > 0)
                System.out.println(user.getUserType() + " registered successfully!");

        } catch (SQLException e) {
            System.out.println("Error while registering user.");
            e.printStackTrace();
        }
    }

    // Find user by email and return correct subclass
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("userType");
                User user;

                switch (type) {
                    case "Student":
                        user = new Student();
                        break;
                    case "Professional":
                        user = new Professional();
                        break;
                    default:
                        throw new IllegalStateException("Unknown user type: " + type);
                }

                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPasswordHash(rs.getString("passwordHash"));

                return user;
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching user.");
            e.printStackTrace();
        }
        return null;
    }
}
