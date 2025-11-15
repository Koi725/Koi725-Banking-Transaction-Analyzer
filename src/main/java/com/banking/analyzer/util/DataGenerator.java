package com.banking.analyzer.util;

import com.banking.analyzer.core.domain.Account;
import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.core.domain.Transaction;
import com.banking.analyzer.core.domain.TransactionType;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * Utility class for generating sample data for testing and demonstration.
 * Follows utility class best practices with private constructor.
 */
public final class DataGenerator {
    
    private static final Random RANDOM = new Random();
    private static final String[] ACCOUNT_HOLDERS = {
        "João Silva", "Maria Santos", "Pedro Costa", "Ana Oliveira",
        "Carlos Pereira", "Sofia Rodrigues", "Miguel Alves", "Rita Fernandes"
    };
    private static final String[] MERCHANTS = {
        "SuperMarket Central", "Tech Store", "Coffee Shop", "Restaurant Bella",
        "Gas Station", "Online Marketplace", "Pharmacy Plus", "Book Store"
    };
    private static final String[] CATEGORIES = {
        "Groceries", "Electronics", "Food & Dining", "Transportation",
        "Healthcare", "Shopping", "Entertainment", "Utilities"
    };
    
    // Private constructor prevents instantiation
    private DataGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Generates a random account with realistic data.
     *
     * @param type the account type
     * @return a new Account instance
     */
    public static Account generateRandomAccount(Account.AccountType type) {
        String accountNumber = generateAccountNumber();
        String holderName = ACCOUNT_HOLDERS[RANDOM.nextInt(ACCOUNT_HOLDERS.length)];
        double initialBalance = 1000.0 + (RANDOM.nextDouble() * 9000.0);
        
        return Account.builder()
            .accountId(UUID.randomUUID().toString())
            .accountNumber(accountNumber)
            .accountHolderName(holderName)
            .type(type)
            .balance(Money.of(initialBalance, "EUR"))
            .active(true)
            .build();
    }
    
    /**
     * Generates a random transaction for a given account.
     *
     * @param accountId the account ID
     * @return a new Transaction instance
     */
    public static Transaction generateRandomTransaction(String accountId) {
        TransactionType type = getRandomTransactionType();
        double amount = generateRandomAmount(type);
        LocalDateTime timestamp = generateRandomTimestamp();
        String merchant = MERCHANTS[RANDOM.nextInt(MERCHANTS.length)];
        String category = CATEGORIES[RANDOM.nextInt(CATEGORIES.length)];
        
        return Transaction.builder()
            .accountId(accountId)
            .type(type)
            .amount(Money.of(amount, "EUR"))
            .timestamp(timestamp)
            .description(generateDescription(type, merchant))
            .merchantName(merchant)
            .category(category)
            .build();
    }
    
    /**
     * Generates a suspicious transaction for fraud detection testing.
     *
     * @param accountId the account ID
     * @return a suspicious transaction
     */
    public static Transaction generateSuspiciousTransaction(String accountId) {
        LocalDateTime lateNight = LocalDateTime.now()
            .withHour(2)
            .withMinute(RANDOM.nextInt(60));
        
        return Transaction.builder()
            .accountId(accountId)
            .type(TransactionType.WITHDRAWAL)
            .amount(Money.of(15000.0 + (RANDOM.nextDouble() * 5000.0), "EUR"))
            .timestamp(lateNight)
            .description("Large late-night withdrawal")
            .merchantName("ATM Unknown Location")
            .category("Cash Withdrawal")
            .build();
    }
    
    /**
     * Generates a series of rapid transactions for pattern detection.
     *
     * @param accountId the account ID
     * @param count number of transactions
     * @return array of rapid transactions
     */
    public static Transaction[] generateRapidTransactions(String accountId, int count) {
        Transaction[] transactions = new Transaction[count];
        LocalDateTime startTime = LocalDateTime.now().minusHours(1);
        
        for (int i = 0; i < count; i++) {
            transactions[i] = Transaction.builder()
                .accountId(accountId)
                .type(TransactionType.PAYMENT)
                .amount(Money.of(50.0 + (RANDOM.nextDouble() * 100.0), "EUR"))
                .timestamp(startTime.plusMinutes(i * 2))
                .description("Rapid transaction " + (i + 1))
                .merchantName(MERCHANTS[RANDOM.nextInt(MERCHANTS.length)])
                .category("Online Payment")
                .build();
        }
        
        return transactions;
    }
    
    private static String generateAccountNumber() {
        return String.format("PT50%04d%04d%04d%04d%01d",
            RANDOM.nextInt(10000),
            RANDOM.nextInt(10000),
            RANDOM.nextInt(10000),
            RANDOM.nextInt(10000),
            RANDOM.nextInt(10)
        );
    }
    
    private static TransactionType getRandomTransactionType() {
        TransactionType[] types = TransactionType.values();
        return types[RANDOM.nextInt(types.length)];
    }
    
    private static double generateRandomAmount(TransactionType type) {
        double baseAmount = switch (type) {
            case DEPOSIT -> 200.0 + (RANDOM.nextDouble() * 800.0);
            case WITHDRAWAL -> 50.0 + (RANDOM.nextDouble() * 500.0);
            case PAYMENT -> 20.0 + (RANDOM.nextDouble() * 200.0);
            case TRANSFER -> 100.0 + (RANDOM.nextDouble() * 1000.0);
            case REFUND -> 10.0 + (RANDOM.nextDouble() * 100.0);
            case FEE -> 1.0 + (RANDOM.nextDouble() * 20.0);
        };
        
        return Math.round(baseAmount * 100.0) / 100.0;
    }
    
    private static LocalDateTime generateRandomTimestamp() {
        int daysAgo = RANDOM.nextInt(30);
        int hour = RANDOM.nextInt(24);
        int minute = RANDOM.nextInt(60);
        
        return LocalDateTime.now()
            .minusDays(daysAgo)
            .withHour(hour)
            .withMinute(minute)
            .withSecond(RANDOM.nextInt(60));
    }
    
    private static String generateDescription(TransactionType type, String merchant) {
        return switch (type) {
            case DEPOSIT -> "Deposit to account";
            case WITHDRAWAL -> "Cash withdrawal";
            case PAYMENT -> "Payment to " + merchant;
            case TRANSFER -> "Transfer transaction";
            case REFUND -> "Refund from " + merchant;
            case FEE -> "Service fee";
        };
    }
}