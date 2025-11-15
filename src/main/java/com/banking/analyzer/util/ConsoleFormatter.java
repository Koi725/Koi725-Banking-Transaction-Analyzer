package com.banking.analyzer.util;

import com.banking.analyzer.core.domain.Account;
import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.core.domain.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility class for formatting console output.
 */
public final class ConsoleFormatter {
    
    private static final String HORIZONTAL_LINE = "=".repeat(80);
    private static final String SEPARATOR = "-".repeat(80);
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    // Private constructor prevents instantiation
    private ConsoleFormatter() {
        throw new UnsupportedOperationException("Utility class cannt be instantiated");
    }
    
    /**
     * @param title the header title
     */
    public static void printHeader(String title) {
        System.out.println("\n" + HORIZONTAL_LINE);
        System.out.println(centerText(title, 80));
        System.out.println(HORIZONTAL_LINE);
    }
    
    /**
     * @param title the section title
     */
    public static void printSectionHeader(String title) {
        System.out.println("\n" + title);
        System.out.println(SEPARATOR);
    }
    
    /**
     * Prints transaction details in a formatted table.
     *
     * @param transaction the transaction to display
     */
    public static void printTransaction(Transaction transaction) {
        System.out.printf("%-15s: %s%n", "ID", transaction.getTransactionId());
        System.out.printf("%-15s: %s%n", "Account", transaction.getAccountId());
        System.out.printf("%-15s: %s%n", "Type", transaction.getType().getDisplayName());
        System.out.printf("%-15s: %s%n", "Amount", formatMoney(transaction.getAmount()));
        System.out.printf("%-15s: %s%n", "Date", transaction.getTimestamp().format(DATE_FORMATTER));
        System.out.printf("%-15s: %s%n", "Merchant", transaction.getMerchantName());
        System.out.printf("%-15s: %s%n", "Category", transaction.getCategory());
        System.out.printf("%-15s: %s%n", "Suspicious", transaction.isSuspicious() ? "YES" : "NO");
        System.out.println(SEPARATOR);
    }
    
    /**
     * Prints a list of transactions in table format.
     *
     * @param transactions list of transactions
     * @param limit maximum number to display
     */
    public static void printTransactionList(List<Transaction> transactions, int limit) {
        System.out.printf("%-20s %-15s %-15s %-20s %-10s%n",
            "Timestamp", "Type", "Amount", "Merchant", "Suspicious");
        System.out.println(SEPARATOR);
        
        int count = Math.min(transactions.size(), limit);
        for (int i = 0; i < count; i++) {
            Transaction t = transactions.get(i);
            System.out.printf("%-20s %-15s %-15s %-20s %-10s%n",
                t.getTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                t.getType().getDisplayName(),
                formatMoney(t.getAmount()),
                truncate(t.getMerchantName(), 18),
                t.isSuspicious() ? "YES" : "");
        }
        
        if (transactions.size() > limit) {
            System.out.printf("%n... and %d more transactions%n", transactions.size() - limit);
        }
    }
    
    /**
     * @param account the account to display
     */
    public static void printAccount(Account account) {
        System.out.printf("%-20s: %s%n", "Account Number", account.getAccountNumber());
        System.out.printf("%-20s: %s%n", "Holder", account.getAccountHolderName());
        System.out.printf("%-20s: %s%n", "Type", account.getType());
        System.out.printf("%-20s: %s%n", "Balance", formatMoney(account.getBalance()));
        System.out.printf("%-20s: %s%n", "Status", account.isActive() ? "Active" : "Inactive");
        System.out.printf("%-20s: %s%n", "Created", 
            account.getCreatedAt().format(DATE_FORMATTER));
        System.out.println(SEPARATOR);
    }
    
    /**
     * Prints a statistical summary.
     *
     * @param label the statistic label
     * @param value the value
     */
    public static void printStatistic(String label, Object value) {
        System.out.printf("%-30s: %s%n", label, value);
    }
    
    /**
     * Prints a success message.
     *
     * @param message the message
     */
    public static void printSuccess(String message) {
        System.out.println("✓ " + message);
    }
    
    /**
     * Prints an error message.
     *
     * @param message the message
     */
    public static void printError(String message) {
        System.err.println("✗ ERROR: " + message);
    }
    
    /**
     * Prints a warning message.
     *
     * @param message the message
     */
    public static void printWarning(String message) {
        System.out.println("⚠ WARNING: " + message);
    }
    
    /**
     * Prints an info message.
     *
     * @param message the message
     */
    public static void printInfo(String message) {
        System.out.println("ℹ " + message);
    }
    
    /**
     * Formats money for display.
     *
     * @param money the money object
     * @return formatted string
     */
    private static String formatMoney(Money money) {
        return String.format("%s %.2f", money.getCurrency(), money.getAmount());
    }
    
    /**
     * @param text the text to center
     * @param width the total width
     * @return centered text
     */
    private static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
    
    /**
     * @param text the text to truncate
     * @param maxLength maximum length
     * @return truncated text
     */
    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }
}