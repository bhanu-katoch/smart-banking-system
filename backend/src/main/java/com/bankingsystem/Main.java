package com.bankingsystem;

import com.bankingsystem.config.DatabaseConfig;
import com.bankingsystem.controller.TerminalController;
// import com.bankingsystem.controller.TerminalControllerJline;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig.init();
        TerminalController controller = new TerminalController();
        // TerminalControllerJline controller = new TerminalControllerJline();
        controller.start();
    }
}
