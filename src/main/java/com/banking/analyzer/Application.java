package com.banking.analyzer;

import com.banking.analyzer.core.domain.Account;
import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.core.domain.Transaction;
import com.banking.analyzer.core.domain.TransactionType;
import com.banking.analyzer.core.service.AccountService;
import com.banking.analyzer.core.service.TransactionService;
import com.banking.analyzer.datastructures.list.CustomLinkedList;
import com.banking.analyzer.util.ConsoleFormatter;
import com.banking.analyzer.util.DataGenerator;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Main application demonstrating the Banking Transaction Analyzer system.
 * Showcases all data structures: Lists, Trees, and Recursive algorithms.
 */
public class Application {
    
    private final AccountService accountService;
    private final TransactionService transactionService;
    
    public Application() {
        this.accountService = new AccountService();
        this.transactionService = new TransactionService();
    }
    
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
    
    /**
     * Main execution method demonstrating all system capabilities.
     */
    public void run() {
        ConsoleFormatter.printHeader("BANKING TRANSACTION ANALYZER SYSTEM");
        
        try {
            // Demonstrate account management with BST
            demonstrateAccountManagement();
            
            // Demonstrate transaction processing with LinkedList
            demonstrateTransactionProcessing();
            
            // Demonstrate recursive algorithms
            demonstrateRecursiveAnalysis();
            
            // Demonstrate fraud detection
            demonstrateFraudDetection();
            
            // Display final statistics
            displaySystemStatistics();
            
            ConsoleFormatter.printSuccess("System demonstration completed successfully!");
            
        } catch (Exception e) {
            ConsoleFormatter.printError("System error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Demonstrates account management using Binary Search Tree.
     */
    private void demonstrateAccountManagement() {
        ConsoleFormatter.printSectionHeader("1. ACCOUNT MANAGEMENT (Binary Search Tree)");
        
        // Create sample accounts demonstrating tree structure
        ConsoleFormatter.printInfo("Creating accounts in BST...");
        
        Account[] accounts = {
            DataGenerator.generateRandomAccount(Account.AccountType.PERSONAL),
            DataGenerator.generateRandomAccount(Account.AccountType.BUSINESS),
            DataGenerator.generateRandomAccount(Account.AccountType.CORPORATE),
            DataGenerator.generateRandomAccount(Account.AccountType.SAVINGS),
            DataGenerator.generateRandomAccount(Account.AccountType.CHECKING)
        };
        
        for (Account account : accounts) {
            accountService.createAccount(account);
            ConsoleFormatter.printSuccess("Created account: " + account.getAccountNumber());
        }
        
        System.out.println();
        ConsoleFormatter.printStatistic("Total accounts in tree", accountService.getAccountCount());
        ConsoleFormatter.printStatistic("Total balance across all accounts", 
            accountService.calculateTotalBalance());
        
        // Demonstrate tree search
        System.out.println();
        ConsoleFormatter.printInfo("Searching for account using BST search...");
        Account foundAccount = accountService.findAccount(accounts[0].getAccountNumber());
        if (foundAccount != null) {
            ConsoleFormatter.printSuccess("Account found!");
            ConsoleFormatter.printAccount(foundAccount);
        }
        
        // Demonstrate sorted retrieval (in-order traversal)
        System.out.println();
        ConsoleFormatter.printInfo("Retrieving all accounts in sorted order (in-order traversal):");
        List<Account> sortedAccounts = accountService.getAllAccountsSorted();
        for (Account account : sortedAccounts) {
            System.out.printf("  %s - %s - %s%n", 
                account.getAccountNumber(),
                account.getAccountHolderName(),
                account.getBalance());
        }
    }
    
    /**
     * Demonstrates transaction processing using Custom Linked List.
     */
    private void demonstrateTransactionProcessing() {
        ConsoleFormatter.printSectionHeader("2. TRANSACTION PROCESSING (Custom Linked List)");
        
        // Get first account for transactions
        List<Account> accounts = accountService.getAllAccountsSorted();
        if (accounts.isEmpty()) {
            ConsoleFormatter.printWarning("No accounts available for transactions");
            return;
        }
        
        Account testAccount = accounts.get(0);
        ConsoleFormatter.printInfo("Processing transactions for account: " + 
            testAccount.getAccountNumber());
        
        // Generate and process various transactions
        System.out.println();
        ConsoleFormatter.printInfo("Adding transactions to linked list...");
        
        for (int i = 0; i < 15; i++) {
            Transaction transaction = DataGenerator.generateRandomTransaction(
                testAccount.getAccountId()
            );
            transactionService.processTransaction(transaction);
        }
        
        ConsoleFormatter.printSuccess("Added 15 transactions to the list");
        ConsoleFormatter.printStatistic("Total transactions in list", 
            transactionService.getTransactionCount());
        
        // Demonstrate list filtering
        System.out.println();
        ConsoleFormatter.printInfo("Filtering transactions by type (Payment):");
        CustomLinkedList<Transaction> payments = transactionService
            .getTransactionsByType(TransactionType.PAYMENT);
        ConsoleFormatter.printStatistic("Payment transactions found", payments.size());
        
        // Display sample transactions
        System.out.println();
        ConsoleFormatter.printInfo("Sample transactions from linked list:");
        CustomLinkedList<Transaction> allTransactions = transactionService.getAllTransactions();
        int displayCount = Math.min(5, allTransactions.size());
        for (int i = 0; i < displayCount; i++) {
            Transaction t = allTransactions.get(i);
            System.out.printf("  [%d] %s - %s - %s - %s%n",
                i + 1,
                t.getTimestamp().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                t.getType().getDisplayName(),
                t.getAmount(),
                t.getMerchantName());
        }
    }
    
    /**
     * Demonstrates recursive algorithms for transaction analysis.
     */
    private void demonstrateRecursiveAnalysis() {
        ConsoleFormatter.printSectionHeader("3. RECURSIVE ANALYSIS ALGORITHMS");
        
        if (transactionService.getTransactionCount() == 0) {
            ConsoleFormatter.printWarning("No transactions available for analysis");
            return;
        }
        
        // Recursive total calculation
        ConsoleFormatter.printInfo("Calculating total volume using recursion...");
        Money totalVolume = transactionService.calculateTotalVolume();
        ConsoleFormatter.printStatistic("Total transaction volume (recursive)", totalVolume);
        
        // Recursive maximum finding (divide and conquer)
        System.out.println();
        ConsoleFormatter.printInfo("Finding largest transaction using divide-and-conquer...");
        Money maxTransaction = transactionService.findLargestTransaction();
        ConsoleFormatter.printStatistic("Largest transaction (recursive)", maxTransaction);
        
        // Recursive average calculation
        System.out.println();
        ConsoleFormatter.printInfo("Calculating average using recursion...");
        Money avgTransaction = transactionService.calculateAverageTransaction();
        ConsoleFormatter.printStatistic("Average transaction amount (recursive)", avgTransaction);
        
        // Recursive counting
        System.out.println();
        ConsoleFormatter.printInfo("Counting high-value transactions (recursive)...");
        Money threshold = Money.of(100.0, "EUR");
        int highValueCount = transactionService.countHighValueTransactions(threshold);
        ConsoleFormatter.printStatistic("Transactions above €100 (recursive count)", 
            highValueCount);
    }
    
    /**
     * Demonstrates fraud detection using recursive pattern matching.
     */
    private void demonstrateFraudDetection() {
        ConsoleFormatter.printSectionHeader("4. FRAUD DETECTION (Recursive Pattern Analysis)");
        
        // Add suspicious transaction
        List<Account> accounts = accountService.getAllAccountsSorted();
        if (!accounts.isEmpty()) {
            Account testAccount = accounts.get(0);
            
            ConsoleFormatter.printInfo("Adding suspicious transactions...");
            Transaction suspicious = DataGenerator.generateSuspiciousTransaction(
                testAccount.getAccountId()
            );
            transactionService.processTransaction(suspicious);
            ConsoleFormatter.printSuccess("Suspicious transaction added");
            
            // Add rapid transactions
            System.out.println();
            ConsoleFormatter.printInfo("Adding rapid transaction sequence...");
            Transaction[] rapidTxns = DataGenerator.generateRapidTransactions(
                testAccount.getAccountId(), 6
            );
            for (Transaction txn : rapidTxns) {
                transactionService.processTransaction(txn);
            }
            ConsoleFormatter.printSuccess("Added 6 rapid transactions");
        }
        
        // Detect suspicious patterns
        System.out.println();
        ConsoleFormatter.printInfo("Running recursive fraud detection algorithms...");
        
        boolean suspiciousActivityDetected = transactionService.detectSuspiciousActivity();
        if (suspiciousActivityDetected) {
            ConsoleFormatter.printWarning("ALERT: Suspicious activity pattern detected!");
        } else {
            ConsoleFormatter.printSuccess("No suspicious patterns detected");
        }
        
        // Get flagged transactions
        System.out.println();
        CustomLinkedList<Transaction> flaggedTransactions = 
            transactionService.getSuspiciousTransactions();
        ConsoleFormatter.printStatistic("Flagged transactions", flaggedTransactions.size());
        
        if (flaggedTransactions.size() > 0) {
            System.out.println();
            ConsoleFormatter.printInfo("Suspicious transactions:");
            for (int i = 0; i < Math.min(3, flaggedTransactions.size()); i++) {
                Transaction t = flaggedTransactions.get(i);
                System.out.printf("  ⚠ %s - %s - %s at %s%n",
                    t.getType().getDisplayName(),
                    t.getAmount(),
                    t.getMerchantName(),
                    t.getTimestamp().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    ));
            }
        }
    }
    
    /**
     * Displays comprehensive system statistics.
     */
    private void displaySystemStatistics() {
        ConsoleFormatter.printSectionHeader("5. SYSTEM STATISTICS SUMMARY");
        
        // Account statistics (from BST)
        ConsoleFormatter.printInfo("Account Management (BST):");
        ConsoleFormatter.printStatistic("  Total accounts", accountService.getAccountCount());
        ConsoleFormatter.printStatistic("  Total system balance", 
            accountService.calculateTotalBalance());
        
        Money highBalanceThreshold = Money.of(5000.0, "EUR");
        List<Account> highBalanceAccounts = accountService
            .findAccountsAboveBalance(highBalanceThreshold);
        ConsoleFormatter.printStatistic("  Accounts with balance > €5000", 
            highBalanceAccounts.size());
        
        // Transaction statistics (from LinkedList)
        System.out.println();
        ConsoleFormatter.printInfo("Transaction Processing (Linked List):");
        ConsoleFormatter.printStatistic("  Total transactions", 
            transactionService.getTransactionCount());
        ConsoleFormatter.printStatistic("  Total volume", 
            transactionService.calculateTotalVolume());
        ConsoleFormatter.printStatistic("  Average transaction", 
            transactionService.calculateAverageTransaction());
        
        // Fraud statistics (from Recursive Analysis)
        System.out.println();
        ConsoleFormatter.printInfo("Fraud Detection (Recursive Algorithms):");
        ConsoleFormatter.printStatistic("  Suspicious transactions found", 
            transactionService.getSuspiciousTransactions().size());
        ConsoleFormatter.printStatistic("  Rapid pattern detected", 
            transactionService.detectSuspiciousActivity() ? "YES" : "NO");
    }
}