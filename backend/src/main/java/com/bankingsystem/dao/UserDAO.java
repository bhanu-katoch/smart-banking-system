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
                PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getUserType());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        int userId = keys.getInt(1);
                        user.setId(userId);
                        // Insert into child table depending on type
                        if (user instanceof Student) {
                            String insertStudent = "INSERT INTO students(userId, college) VALUES (?, ?)";
                            try (PreparedStatement ps = conn.prepareStatement(insertStudent)) {
                                ps.setInt(1, userId);
                                ps.setString(2, ((Student) user).getCollege());
                                ps.executeUpdate();
                            }
                        } else if (user instanceof Professional) {
                            String insertProf = "INSERT INTO professionals(userId, company) VALUES (?, ?)";
                            try (PreparedStatement ps = conn.prepareStatement(insertProf)) {
                                ps.setInt(1, userId);
                                ps.setString(2, ((Professional) user).getCompany());
                                ps.executeUpdate();
                            }
                        } else if (user instanceof Customer) {
                            String insertCust = "INSERT INTO customers(userId, address, phone) VALUES (?, ?, ?)";
                            try (PreparedStatement ps = conn.prepareStatement(insertCust)) {
                                ps.setInt(1, userId);
                                ps.setString(2, ((Customer) user).getAddress());
                                ps.setString(3, ((Customer) user).getPhone());
                                ps.executeUpdate();
                            }
                        }
                    }
                }
                System.out.println(user.getUserType() + " registered successfully!");
            }

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

                int userId = rs.getInt("id");

                switch (type) {
                    case "Student":
                        Student s = new Student();
                        // fetch student-specific data
                        String ssql = "SELECT college FROM students WHERE userId = ?";
                        try (PreparedStatement sps = conn.prepareStatement(ssql)) {
                            sps.setInt(1, userId);
                            try (ResultSet srs = sps.executeQuery()) {
                                if (srs.next()) {
                                    s.setCollege(srs.getString("college"));
                                }
                            }
                        }
                        user = s;
                        break;
                    case "Professional":
                        Professional p = new Professional();
                        String psql = "SELECT company FROM professionals WHERE userId = ?";
                        try (PreparedStatement pps = conn.prepareStatement(psql)) {
                            pps.setInt(1, userId);
                            try (ResultSet prs = pps.executeQuery()) {
                                if (prs.next()) {
                                    p.setCompany(prs.getString("company"));
                                }
                            }
                        }
                        user = p;
                        break;
                    case "Customer":
                        Customer c = new Customer();
                        String csql = "SELECT address, phone FROM customers WHERE userId = ?";
                        try (PreparedStatement cps = conn.prepareStatement(csql)) {
                            cps.setInt(1, userId);
                            try (ResultSet crs = cps.executeQuery()) {
                                if (crs.next()) {
                                    c.setAddress(crs.getString("address"));
                                    c.setPhone(crs.getString("phone"));
                                }
                            }
                        }
                        user = c;
                        break;
                    default:
                        throw new IllegalStateException("Unknown user type: " + type);
                }

                user.setId(userId);
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
