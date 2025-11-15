package com.banking.analyzer.core.service;

import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.core.domain.Transaction;
import com.banking.analyzer.core.domain.TransactionType;
import com.banking.analyzer.datastructures.list.CustomLinkedList;
import com.banking.analyzer.datastructures.recursion.RecursiveTransactionAnalyzer;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Service layer for transaction-related business operations.
 * Encapsulates business logic and coordinates between data structures.
 */
public class TransactionService {
    
    private final CustomLinkedList<Transaction> transactionHistory;
    private final RecursiveTransactionAnalyzer analyzer;
    
    public TransactionService() {
        this.transactionHistory = new CustomLinkedList<>();
        this.analyzer = new RecursiveTransactionAnalyzer();
    }
    
    /**
     * Processes and stores a new transaction.
     *
     * @param transaction the transaction to process
     * @throws IllegalArgumentException if transaction is invalid
     */
    public void processTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        
        validateTransaction(transaction);
        
        transactionHistory.add(transaction);
    }
    
    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId the account ID
     * @return list of transactions for the account
     */
    public CustomLinkedList<Transaction> getTransactionsByAccount(String accountId) {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        
        return transactionHistory.filter(t -> t.getAccountId().equals(accountId));
    }
    
    /**
     * Retrieves transactions within a date range.
     *
     * @param startDate start of date range
     * @param endDate end of date range
     * @return filtered list of transactions
     */
    public CustomLinkedList<Transaction> getTransactionsByDateRange(LocalDateTime startDate,
                                                                      LocalDateTime endDate) {
        Objects.requireNonNull(startDate, "Start date cannot be null");
        Objects.requireNonNull(endDate, "End date cannot be null");
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        return transactionHistory.filter(t -> 
            !t.getTimestamp().isBefore(startDate) && 
            !t.getTimestamp().isAfter(endDate)
        );
    }
    
    /**
     * Retrieves transactions by type.
     *
     * @param type the transaction type
     * @return filtered list of transactions
     */
    public CustomLinkedList<Transaction> getTransactionsByType(TransactionType type) {
        Objects.requireNonNull(type, "Transaction type cannot be null");
        
        return transactionHistory.filter(t -> t.getType() == type);
    }
    
    /**
     * Calculates total transaction volume using recursive algorithm.
     *
     * @return total Money amount of all transactions
     */
    public Money calculateTotalVolume() {
        if (transactionHistory.isEmpty()) {
            return Money.zero("EUR");
        }
        
        return analyzer.calculateTotalRecursive(transactionHistory, 0);
    }
    
    /**
     * Finds the largest transaction using recursive divide-and-conquer.
     *
     * @return the maximum transaction amount
     */
    public Money findLargestTransaction() {
        if (transactionHistory.isEmpty()) {
            return Money.zero("EUR");
        }
        
        return analyzer.findMaxTransactionRecursive(
            transactionHistory, 
            0, 
            transactionHistory.size() - 1
        );
    }
    
    /**
     * Calculates average transaction amount.
     *
     * @return average Money amount
     */
    public Money calculateAverageTransaction() {
        if (transactionHistory.isEmpty()) {
            return Money.zero("EUR");
        }
        
        return analyzer.calculateAverageRecursive(transactionHistory, 0);
    }
    
    /**
     * Detects suspicious transaction patterns using recursive analysis.
     *
     * @return true if suspicious pattern detected
     */
    public boolean detectSuspiciousActivity() {
        if (transactionHistory.size() < 3) {
            return false;
        }
        
        // Check for rapid transaction pattern (5+ transactions within 10 minutes)
        return analyzer.detectRapidTransactionPattern(
            transactionHistory, 
            0, 
            1, 
            5, 
            10
        );
    }
    
    /**
     * Counts high-value transactions above threshold.
     *
     * @param threshold the amount threshold
     * @return count of transactions above threshold
     */
    public int countHighValueTransactions(Money threshold) {
        Objects.requireNonNull(threshold, "Threshold cannot be null");
        
        return analyzer.countLargeTransactions(transactionHistory, 0, threshold, 0);
    }
    
    /**
     * Retrieves all suspicious transactions.
     *
     * @return list of flagged transactions
     */
    public CustomLinkedList<Transaction> getSuspiciousTransactions() {
        return transactionHistory.filter(Transaction::isSuspicious);
    }
    
    /**
     * Gets transaction count.
     *
     * @return total number of transactions
     */
    public int getTransactionCount() {
        return transactionHistory.size();
    }
    
    /**
     * Retrieves complete transaction history.
     *
     * @return all transactions
     */
    public CustomLinkedList<Transaction> getAllTransactions() {
        return transactionHistory;
    }
    
    /**
     * Clears all transaction history.
     */
    public void clearHistory() {
        transactionHistory.clear();
    }
    
    /**
     * Validates a transaction according to business rules.
     *
     * @param transaction the transaction to validate
     * @throws IllegalArgumentException if transaction is invalid
     */
    private void validateTransaction(Transaction transaction) {
        if (!transaction.getAmount().isPositive() && 
            transaction.getType() != TransactionType.REFUND) {
            throw new IllegalArgumentException(
                "Transaction amount must be positive for type: " + transaction.getType()
            );
        }
        
        if (transaction.getTimestamp().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                "Transaction timestamp cannot be in the future"
            );
        }
        
        if (!analyzer.validateTransactionHierarchy(transaction, 0, 10)) {
            throw new IllegalArgumentException(
                "Transaction failed hierarchy validation"
            );
        }
    }
}