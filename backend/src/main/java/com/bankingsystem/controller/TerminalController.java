package com.bankingsystem.controller;

import java.util.Scanner;
import com.bankingsystem.service.AuthService;
import com.bankingsystem.model.User;

public class TerminalController {
    private AuthService authService = new AuthService();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Welcome to Smart Banking System!");

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit\nChoice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerFlow();
                    break;
                case 2:
                    loginFlow();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void registerFlow() {
        System.out.println("\n--- Register ---");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter user type (Student/Professional): ");
        String userType = scanner.nextLine();

        boolean success = authService.register(name, email, password, userType);
        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Email might already exist.");
        }
    }

    private void loginFlow() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = authService.login(email, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getUserType() + ")");
        } else {
            System.out.println("Login failed. Incorrect email or password.");
        }
    }
}
