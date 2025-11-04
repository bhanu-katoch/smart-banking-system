package com.bankingsystem.controller;

import java.util.Scanner;
import com.bankingsystem.model.Account;
import com.bankingsystem.model.User;
import com.bankingsystem.service.AccountService;
import com.bankingsystem.service.AuthService;
import com.bankingsystem.service.TransactionService;
import com.bankingsystem.util.SuggestionUtils;
import java.util.concurrent.ThreadLocalRandom;

public class TerminalController {

    private AuthService authService = new AuthService();
    private AccountService accountService = new AccountService();
    private TransactionService transactionService = new TransactionService();
    private Scanner scanner = new Scanner(System.in);

    // currently logged-in user
    private User currentUser = null;

    // ANSI colors for terminal UI
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private void printHeader(String title) {
        String line = CYAN + "==============================================" + RESET;
        System.out.println(line);
        System.out.println(CYAN + BOLD + "  " + title + RESET);
        System.out.println(line);
    }

    private void printGuestMenu() {
        System.out.println(YELLOW + "\n1. Register" + RESET);
        System.out.println(YELLOW + "2. Login" + RESET);
        System.out.println(YELLOW + "3. Exit" + RESET);
        System.out.print(BLUE + "Choice: " + RESET);
    }

    private void printUserMenu() {
        System.out.println(MAGENTA + "\n1. Create Account" + RESET);
        System.out.println(MAGENTA + "2. Deposit" + RESET);
        System.out.println(MAGENTA + "3. Withdraw" + RESET);
        System.out.println(MAGENTA + "4. Check Balance" + RESET);
        System.out.println(MAGENTA + "5. Show My Details" + RESET);
        System.out.println(MAGENTA + "6. Show Transactions" + RESET);
        System.out.println(MAGENTA + "7. Logout" + RESET);
        System.out.print(BLUE + "Choice: " + RESET);
    }

    public void start() {
        printHeader("Welcome to Smart Banking System!");

        while (true) {
            if (currentUser == null) {
                printGuestMenu();
                int choice = readInt();
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
            } else {
                printUserMenu();
                int choice = readInt();
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        checkBalance();
                        break;
                    case 5:
                        showMyDetails();
                        break;
                    case 6:
                        showTransactions();
                        break;
                    case 7:
                        System.out.println(GREEN + "Logged out: " + currentUser.getName() + RESET);
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    private int readInt() {
        while (true) {
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount: ");
            }
        }
    }

    private boolean readYesNo() {
        while (true) {
            String s = scanner.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes"))
                return true;
            if (s.equals("n") || s.equals("no"))
                return false;
            System.out.print("Please enter 'y' or 'n': ");
        }
    }

    private void registerFlow() {
        printHeader("Register");
        System.out.print(CYAN + "Enter name: " + RESET);
        String name = scanner.nextLine();

        System.out.print(CYAN + "Enter email: " + RESET);
        String email = scanner.nextLine();

        System.out.print(CYAN + "Enter password: " + RESET);
        String password = scanner.nextLine();

        System.out.print(CYAN + "Enter user type (Student/Professional/Customer): " + RESET);
        String userType = scanner.nextLine();

        // Collect per-type details
        String extra1 = null;
        String extra2 = null;
        if (userType != null) {
            String t = userType.trim().toLowerCase();
            if (t.equals("student")) {
                System.out.print(CYAN + "Enter college name: " + RESET);
                extra1 = scanner.nextLine();
            } else if (t.equals("professional")) {
                System.out.print(CYAN + "Enter company name: " + RESET);
                extra1 = scanner.nextLine();
            } else if (t.equals("customer")) {
                System.out.print(CYAN + "Enter address: " + RESET);
                extra1 = scanner.nextLine();
                System.out.print(CYAN + "Enter phone number: " + RESET);
                extra2 = scanner.nextLine();
            }
        }

        boolean success = authService.register(name, email, password, userType, extra1, extra2);
        if (success) {
            System.out.println(GREEN + "\n✔ Registration successful! Please login to continue." + RESET);
        } else {
            System.out.println(RED + "\n✖ Registration failed. Email might already exist." + RESET);
        }
    }

    private void loginFlow() {
        printHeader("Login");
        System.out.print(CYAN + "Enter email: " + RESET);
        String email = scanner.nextLine();

        System.out.print(CYAN + "Enter password: " + RESET);
        String password = scanner.nextLine();

        User user = authService.login(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println(GREEN + "\n✔ Login successful! Welcome, " + user.getName() + " (" + user.getUserType()
                    + ")" + RESET);
        } else {
            System.out.println(RED + "\n✖ Login failed. Incorrect email or password." + RESET);
        }
    }

    private void createAccount() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("\n--- Create Account ---");

        // If user already has an account, refuse immediately
        Account existing = accountService.getAccountByUserId(currentUser.getId());
        if (existing != null) {
            System.out.println(YELLOW + "You already have an account." + RESET);
            System.out.println(CYAN + "Account Number: " + existing.getAccountNumber() + " | Balance: "
                    + existing.getBalance() + RESET);
            return;
        }

        // generate a random 10-digit account number (first digit non-zero)
        String acctNum = null;
        int attempts = 0;
        do {
            long num = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
            acctNum = Long.toString(num);
            attempts++;
        } while (accountService.accountExists(acctNum) && attempts < 5);

        if (attempts >= 5 && accountService.accountExists(acctNum)) {
            System.out.println(RED + "Failed to generate a unique account number; try again later." + RESET);
            return;
        }

        System.out.println(MAGENTA + "Generated account number: " + acctNum + RESET);
        System.out.print(CYAN + "Enter initial balance: " + RESET);
        double initial = readDouble();

        Account created = accountService.createAccountIfNone(currentUser.getId(), acctNum, initial);
        if (created != null) {
            System.out.println(GREEN + "\n✔ Account created successfully." + RESET);
            System.out.println(CYAN + "Account Number: " + created.getAccountNumber() + " | Balance: "
                    + created.getBalance() + RESET);
        } else {
            System.out.println(RED + "An account already exists for this user. Cannot create another." + RESET);
        }
    }

    private void deposit() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        Account acc = accountService.getAccountByUserId(currentUser.getId());
        if (acc == null) {
            System.out.println("No account found for your user. Create one first.");
            return;
        }
        System.out.print(CYAN + "Enter deposit amount: " + RESET);
        double amt = readDouble();

        String suggestion = SuggestionUtils.suggest(currentUser.getUserType(), "deposit", amt, acc.getBalance());
        if (suggestion != null && !suggestion.isEmpty()) {
            System.out.println(YELLOW + "\n-- Suggestion --" + RESET);
            System.out.println(YELLOW + suggestion + RESET);
            System.out.print(BLUE + "Proceed with deposit? (y/n): " + RESET);
            if (!readYesNo()) {
                System.out.println(YELLOW + "Deposit cancelled by user." + RESET);
                return;
            }
        }

        System.out.print(CYAN + "Enter a short note/description for this deposit (optional): " + RESET);
        String note = scanner.nextLine();

        boolean ok = transactionService.deposit(acc.getId(), amt, note == null ? "" : note);
        if (ok)
            System.out.println(GREEN + "✔ Deposit completed. New balance: "
                    + accountService.checkBalance(currentUser.getId()) + RESET);
        else
            System.out.println(RED + "✖ Deposit failed." + RESET);
    }

    private void withdraw() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        Account acc = accountService.getAccountByUserId(currentUser.getId());
        if (acc == null) {
            System.out.println("No account found for your user. Create one first.");
            return;
        }
        System.out.print(CYAN + "Enter withdrawal amount: " + RESET);
        double amt = readDouble();

        String suggestion = SuggestionUtils.suggest(currentUser.getUserType(), "withdraw", amt, acc.getBalance());
        if (suggestion != null && !suggestion.isEmpty()) {
            System.out.println(YELLOW + "\n-- Suggestion --" + RESET);
            System.out.println(YELLOW + suggestion + RESET);
            System.out.print(BLUE + "Proceed with withdrawal? (y/n): " + RESET);
            if (!readYesNo()) {
                System.out.println(YELLOW + "Withdrawal cancelled by user." + RESET);
                return;
            }
        }

        System.out.print(CYAN + "Enter a short note/description for this withdrawal (optional): " + RESET);
        String note = scanner.nextLine();

        boolean ok = transactionService.withdraw(acc.getId(), amt, note == null ? "" : note);
        if (ok)
            System.out.println(GREEN + "✔ Withdrawal completed. New balance: "
                    + accountService.checkBalance(currentUser.getId()) + RESET);
        else
            System.out.println(RED + "✖ Withdrawal failed (insufficient funds or error)." + RESET);
    }

    private void showMyDetails() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        printHeader("My Details");
        System.out.println(CYAN + "Name: " + currentUser.getName() + RESET);
        System.out.println(CYAN + "Email: " + currentUser.getEmail() + RESET);
        System.out.println(CYAN + "User Type: " + currentUser.getUserType() + RESET);
        // show per-type fields
        switch (currentUser.getUserType()) {
            case "Student":
                if (currentUser instanceof com.bankingsystem.model.Student) {
                    System.out.println(
                            CYAN + "College: " + ((com.bankingsystem.model.Student) currentUser).getCollege() + RESET);
                }
                break;
            case "Professional":
                if (currentUser instanceof com.bankingsystem.model.Professional) {
                    System.out.println(CYAN + "Company: "
                            + ((com.bankingsystem.model.Professional) currentUser).getCompany() + RESET);
                }
                break;
            case "Customer":
                if (currentUser instanceof com.bankingsystem.model.Customer) {
                    System.out.println(
                            CYAN + "Address: " + ((com.bankingsystem.model.Customer) currentUser).getAddress() + RESET);
                    System.out.println(
                            CYAN + "Phone: " + ((com.bankingsystem.model.Customer) currentUser).getPhone() + RESET);
                }
                break;
            default:
                break;
        }
        // show account and balance
        com.bankingsystem.model.Account acc = accountService.getAccountByUserId(currentUser.getId());
        if (acc != null) {
            System.out.println(
                    CYAN + "Account Number: " + acc.getAccountNumber() + " | Balance: " + acc.getBalance() + RESET);
        }
    }

    private void showTransactions() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        com.bankingsystem.model.Account acc = accountService.getAccountByUserId(currentUser.getId());
        if (acc == null) {
            System.out.println(YELLOW + "No account found for your user." + RESET);
            return;
        }
        printHeader("Transaction History");
        java.util.List<com.bankingsystem.model.TransactionRecord> records = transactionService
                .getTransactionHistory(acc.getId());
        if (records == null || records.isEmpty()) {
            System.out.println(YELLOW + "No transactions found." + RESET);
            return;
        }

        // Print a nice table header
        String headerFmt = "%-5s  %-12s  %10s  %-19s  %s\n";
        System.out.printf(CYAN + BOLD + headerFmt + RESET, "ID", "TYPE", "AMOUNT", "TIMESTAMP", "NOTE");
        System.out.println(CYAN
                + "-----  ------------  ----------  -------------------  --------------------------------" + RESET);

        // For each record, print a row and wrap long notes
        for (com.bankingsystem.model.TransactionRecord r : records) {
            String id = String.valueOf(r.getId());
            String type = r.getType();
            String amount = String.format("%.2f", r.getAmount());
            String ts = r.getTimestamp();
            String note = r.getNote() == null ? "" : r.getNote();

            // width for note column
            int noteWidth = 40;
            if (note.length() <= noteWidth) {
                System.out.printf("%-5s  %-12s  %10s  %-19s  %s\n", id, type, amount, ts, note);
            } else {
                // print first chunk
                System.out.printf("%-5s  %-12s  %10s  %-19s  %s\n", id, type, amount, ts, note.substring(0, noteWidth));
                // print remaining chunks indented under NOTE column
                int start = noteWidth;
                while (start < note.length()) {
                    int end = Math.min(start + noteWidth, note.length());
                    String chunk = note.substring(start, end);
                    System.out.printf("%-5s  %-12s  %10s  %-19s  %s\n", "", "", "", "", chunk);
                    start = end;
                }
            }
        }
    }

    private void checkBalance() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        Account acc = accountService.getAccountByUserId(currentUser.getId());
        if (acc == null) {
            System.out.println(YELLOW + "No account found for your user." + RESET);
            return;
        }
        printHeader("Account Summary");
        System.out.println(
                CYAN + "Account Number: " + acc.getAccountNumber() + " | Current balance: " + acc.getBalance() + RESET);
    }
}
