package com.bankingsystem.service;

import com.bankingsystem.dao.UserDAO;
import com.bankingsystem.model.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    // Register a user
    // extra1 / extra2 are type-specific details:
    // - student: extra1 = college
    // - professional: extra1 = company
    // - customer: extra1 = address, extra2 = phone
    public boolean register(String name, String email, String password, String userType, String extra1, String extra2) {
        // Check if email already exists
        if (userDAO.findByEmail(email) != null) {
            return false; // email already taken
        }

        // Hash password
        String hashedPassword = hashPassword(password);

        // Create correct User subclass based on userType
        User user;
        switch (userType == null ? "" : userType.toLowerCase()) {
            case "student":
                Student s = new Student();
                s.setCollege(extra1 != null ? extra1 : "");
                user = s;
                break;
            case "professional":
                Professional p = new Professional();
                p.setCompany(extra1 != null ? extra1 : "");
                user = p;
                break;
            case "customer":
                Customer c = new Customer();
                c.setAddress(extra1 != null ? extra1 : "");
                c.setPhone(extra2 != null ? extra2 : "");
                user = c;
                break;
            default:
                System.out.println("Invalid user type. Defaulting to Customer.");
                Customer def = new Customer();
                def.setAddress(extra1 != null ? extra1 : "");
                def.setPhone(extra2 != null ? extra2 : "");
                user = def;
        }

        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(hashedPassword);

        // Save user + child-table row
        userDAO.save(user);
        return true;
    }

    // Login a user
    public User login(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user != null && verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // Verify password
    private boolean verifyPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
}
