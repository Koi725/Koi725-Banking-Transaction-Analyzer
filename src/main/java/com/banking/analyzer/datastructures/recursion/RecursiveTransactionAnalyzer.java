package com.banking.analyzer.datastructures.recursion;

import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.core.domain.Transaction;
import com.banking.analyzer.datastructures.list.CustomLinkedList;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Recursive algorithms for transaction analysis and fraud detection.
 * Demonstrates various recursion patterns and techniques.
 */
public class RecursiveTransactionAnalyzer {
    
    /**
     * Recursively calculates the total amount of transactions in a list.
     * Demonstrates basic recursion with list processing.
     *
     * @param transactions the list of transactions
     * @param index current position in the iteration
     * @return total Money amount
     */
    public Money calculateTotalRecursive(CustomLinkedList<Transaction> transactions, int index) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        
        // Base case: reached end of list
        if (index >= transactions.size()) {
            return Money.zero("EUR");
        }
        
        Transaction current = transactions.get(index);
        Money currentAmount = current.getAmount();
        
        // Recursive case: add current amount to sum of remaining
        Money remainingTotal = calculateTotalRecursive(transactions, index + 1);
        
        return currentAmount.add(remainingTotal);
    }
    
    /**
     * Recursively finds the maximum transaction amount.
     * Demonstrates divide-and-conquer approach.
     *
     * @param transactions the list of transactions
     * @param start start index
     * @param end end index
     * @return maximum transaction amount
     */
    public Money findMaxTransactionRecursive(CustomLinkedList<Transaction> transactions, 
                                             int start, int end) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        
        if (transactions.isEmpty()) {
            return Money.zero("EUR");
        }
        
        // Base case: single element
        if (start == end) {
            return transactions.get(start).getAmount();
        }
        
        // Base case: two elements
        if (end - start == 1) {
            Money amount1 = transactions.get(start).getAmount();
            Money amount2 = transactions.get(end).getAmount();
            return amount1.isGreaterThan(amount2) ? amount1 : amount2;
        }
        
        // Recursive case: divide and conquer
        int mid = start + (end - start) / 2;
        Money leftMax = findMaxTransactionRecursive(transactions, start, mid);
        Money rightMax = findMaxTransactionRecursive(transactions, mid + 1, end);
        
        return leftMax.isGreaterThan(rightMax) ? leftMax : rightMax;
    }
    
    /**
     * Recursively detects suspicious transaction patterns.
     * Uses backtracking to find sequences of rapid transactions.
     *
     * @param transactions the list of transactions
     * @param index current position
     * @param sequenceCount current sequence length
     * @param threshold minimum sequence length to flag
     * @param timeWindow time window in minutes for rapid transactions
     * @return true if suspicious pattern detected
     */
    public boolean detectRapidTransactionPattern(CustomLinkedList<Transaction> transactions,
                                                  int index,
                                                  int sequenceCount,
                                                  int threshold,
                                                  long timeWindow) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        
        // Base case: found suspicious pattern
        if (sequenceCount >= threshold) {
            return true;
        }
        
        // Base case: reached end of list
        if (index >= transactions.size()) {
            return false;
        }
        
        Transaction current = transactions.get(index);
        
        // Check if next transaction is within time window
        if (index + 1 < transactions.size()) {
            Transaction next = transactions.get(index + 1);
            long minutesBetween = ChronoUnit.MINUTES.between(
                current.getTimestamp(), 
                next.getTimestamp()
            );
            
            if (Math.abs(minutesBetween) <= timeWindow) {
                // Continue sequence
                return detectRapidTransactionPattern(
                    transactions, 
                    index + 1, 
                    sequenceCount + 1, 
                    threshold, 
                    timeWindow
                );
            } else {
                // Reset sequence
                return detectRapidTransactionPattern(
                    transactions, 
                    index + 1, 
                    1, 
                    threshold, 
                    timeWindow
                );
            }
        }
        
        return false;
    }
    
    /**
     * Recursively validates transaction hierarchy and dependencies.
     * Demonstrates tree-like recursion pattern.
     *
     * @param transaction the transaction to validate
     * @param depth current recursion depth
     * @param maxDepth maximum allowed depth
     * @return true if transaction hierarchy is valid
     */
    public boolean validateTransactionHierarchy(Transaction transaction, 
                                                 int depth, 
                                                 int maxDepth) {
        Objects.requireNonNull(transaction, "Transaction cannot be null");
        
        // Base case: exceeded maximum depth (circular reference protection)
        if (depth > maxDepth) {
            return false;
        }
        
        // Base case: valid transaction
        if (transaction.getAmount().isPositive() && 
            transaction.getTimestamp().isBefore(LocalDateTime.now())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Recursively counts transactions matching a specific criterion.
     * Demonstrates accumulator pattern in recursion.
     *
     * @param transactions the list of transactions
     * @param index current position
     * @param predicate the condition to match (as a simple threshold)
     * @param accumulator current count
     * @return total count of matching transactions
     */
    public int countLargeTransactions(CustomLinkedList<Transaction> transactions,
                                      int index,
                                      Money threshold,
                                      int accumulator) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        Objects.requireNonNull(threshold, "Threshold cannot be null");
        
        // Base case: processed all transactions
        if (index >= transactions.size()) {
            return accumulator;
        }
        
        Transaction current = transactions.get(index);
        int newAccumulator = current.getAmount().isGreaterThan(threshold) 
            ? accumulator + 1 
            : accumulator;
        
        // Recursive case: process next transaction
        return countLargeTransactions(transactions, index + 1, threshold, newAccumulator);
    }
    
    /**
     * Recursively calculates average transaction amount.
     * Demonstrates multiple recursive calls with different operations.
     *
     * @param transactions the list of transactions
     * @param index current position
     * @return average transaction amount
     */
    public Money calculateAverageRecursive(CustomLinkedList<Transaction> transactions, 
                                           int index) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        
        if (transactions.isEmpty()) {
            return Money.zero("EUR");
        }
        
        Money total = calculateTotalRecursive(transactions, index);
        return total.multiply(1.0 / transactions.size());
    }
    
    /**
     * Recursively searches for a transaction by ID using binary search approach.
     * Requires sorted list of transactions.
     *
     * @param transactions sorted list of transactions
     * @param targetId transaction ID to find
     * @param left left boundary
     * @param right right boundary
     * @return found transaction or null
     */
    public Transaction binarySearchTransaction(CustomLinkedList<Transaction> transactions,
                                                String targetId,
                                                int left,
                                                int right) {
        Objects.requireNonNull(transactions, "Transactions list cannot be null");
        Objects.requireNonNull(targetId, "Target ID cannot be null");
        
        // Base case: not found
        if (left > right) {
            return null;
        }
        
        // Calculate middle index
        int mid = left + (right - left) / 2;
        Transaction midTransaction = transactions.get(mid);
        
        int comparison = targetId.compareTo(midTransaction.getTransactionId());
        
        // Base case: found
        if (comparison == 0) {
            return midTransaction;
        }
        
        // Recursive case: search left or right half
        if (comparison < 0) {
            return binarySearchTransaction(transactions, targetId, left, mid - 1);
        } else {
            return binarySearchTransaction(transactions, targetId, mid + 1, right);
        }
    }
    
    /**
     * Recursively generates Fibonacci-based risk score.
     * Demonstrates classic Fibonacci recursion with memoization opportunity.
     *
     * @param n the position in Fibonacci sequence
     * @return Fibonacci number representing risk level
     */
    public long calculateFibonacciRiskScore(int n) {
        if (n <= 1) {
            return n;
        }
        
        return calculateFibonacciRiskScore(n - 1) + calculateFibonacciRiskScore(n - 2);
    }
    
    /**
     * Optimized Fibonacci with memoization for better performance.
     */
    public long calculateFibonacciRiskScoreMemoized(int n, long[] memo) {
        if (n <= 1) {
            return n;
        }
        
        if (memo[n] != 0) {
            return memo[n];
        }
        
        memo[n] = calculateFibonacciRiskScoreMemoized(n - 1, memo) + 
                  calculateFibonacciRiskScoreMemoized(n - 2, memo);
        
        return memo[n];
    }
}