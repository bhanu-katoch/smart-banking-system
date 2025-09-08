package com.bankingsystem;
import com.bankingsystem.config.DatabaseConfig;
import com.bankingsystem.controller.TerminalController;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig.init();
        TerminalController controller = new TerminalController();
        controller.start();
    }
}
