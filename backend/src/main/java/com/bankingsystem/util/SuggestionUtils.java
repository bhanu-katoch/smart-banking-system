package com.bankingsystem.util;

/**
 * Small rule-based suggestion helper to give pre-transaction advice depending
 * on user type.
 * This is intentionally simple and deterministic (no external AI calls).
 */
public class SuggestionUtils {

    /**
     * Return a short suggestion message (may be empty) to show before a
     * transaction.
     *
     * @param userType "Student", "Professional", etc.
     * @param action   "deposit" or "withdraw"
     * @param amount   transaction amount
     * @param balance  current account balance
     * @return suggestion string (empty if no suggestion)
     */
    public static String suggest(String userType, String action, double amount, double balance) {
        if (userType == null)
            userType = "";
        userType = userType.trim();

        StringBuilder sb = new StringBuilder();

        // Normalize action
        String a = action == null ? "" : action.toLowerCase();

        switch (userType) {
            case "Student":
                if ("withdraw".equals(a)) {
                    if (amount > 500)
                        sb.append(
                                "As a Student, consider keeping discretionary withdrawals under $500 to manage monthly expenses.\n");
                    if (balance - amount < 100)
                        sb.append(
                                "Warning: this withdrawal will reduce your balance below $100 — try to maintain an emergency buffer.\n");
                } else if ("deposit".equals(a)) {
                    if (amount < 20)
                        sb.append("Small frequent deposits help build savings — every bit counts.\n");
                }
                sb.append("Tip: Students often benefit from saving a small emergency fund (e.g. $100).");
                break;

            case "Professional":
                if ("withdraw".equals(a)) {
                    if (amount > 2000)
                        sb.append(
                                "As a Professional, large withdrawals (> $2000) may affect upcoming bills or investments.\n");
                    if (balance - amount < 500)
                        sb.append(
                                "Warning: this withdrawal drops balance below $500 — ensure you have enough for recurring expenses.\n");
                } else if ("deposit".equals(a)) {
                    if (amount >= 1000)
                        sb.append(
                                "Good opportunity to allocate part of this deposit to an investment or emergency fund.\n");
                }
                sb.append("Tip: Professionals often keep a larger buffer (e.g. $500) for stability.");
                break;

            default:
                if ("withdraw".equals(a)) {
                    if (balance - amount < 50)
                        sb.append(
                                "Warning: this will reduce balance substantially; consider keeping a small buffer.\n");
                }
                sb.append("General tip: Review your monthly budget before large transactions.");
        }

        return sb.toString().trim();
    }
}
