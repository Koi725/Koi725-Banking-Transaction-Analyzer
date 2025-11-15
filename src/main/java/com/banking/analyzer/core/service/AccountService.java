package com.banking.analyzer.core.service;

import com.banking.analyzer.core.domain.Account;
import com.banking.analyzer.core.domain.Money;
import com.banking.analyzer.datastructures.tree.BinarySearchTree;

import java.util.List;
import java.util.Objects;

/**
 * Service layer for account-related business operations.
 * Uses BST for efficient account storage and retrieval.
 */
public class AccountService {
    
    private final BinarySearchTree<Account> accountTree;
    
    public AccountService() {
        this.accountTree = new BinarySearchTree<>();
    }
    
    /**
     * Creates and stores a new account.
     *
     * @param account the account to create
     * @throws IllegalArgumentException if account already exists
     */
    public void createAccount(Account account) {
        Objects.requireNonNull(account, "Account cannot be null");
        
        if (accountTree.contains(account)) {
            throw new IllegalArgumentException(
                "Account already exists: " + account.getAccountNumber()
            );
        }
        
        accountTree.insert(account);
    }
    
    /**
     * Finds an account by creating a search key.
     *
     * @param accountNumber the account number to search for
     * @return the found account or null
     */
    public Account findAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        
        // Create a search key account
        Account searchKey = Account.builder()
            .accountId("search-key")
            .accountNumber(accountNumber)
            .accountHolderName("Search")
            .type(Account.AccountType.PERSONAL)
            .build();
        
        return accountTree.find(searchKey);
    }
    
    /**
     * Deactivates an account.
     *
     * @param accountNumber the account number
     * @return true if account was deactivated
     */
    public boolean deactivateAccount(String accountNumber) {
        Account account = findAccount(accountNumber);
        
        if (account == null) {
            return false;
        }
        
        account.deactivate();
        return true;
    }
    
    /**
     * Processes a deposit to an account.
     *
     * @param accountNumber the account number
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if account not found
     */
    public void deposit(String accountNumber, Money amount) {
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        
        Account account = findAccount(accountNumber);
        
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        
        account.deposit(amount);
    }
    
    /**
     * Processes a withdrawal from an account.
     *
     * @param accountNumber the account number
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if account not found or insufficient funds
     */
    public void withdraw(String accountNumber, Money amount) {
        Objects.requireNonNull(accountNumber, "Account number cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");
        
        Account account = findAccount(accountNumber);
        
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        
        account.withdraw(amount);
    }
    
    /**
     * Retrieves all accounts in sorted order.
     *
     * @return sorted list of all accounts
     */
    public List<Account> getAllAccountsSorted() {
        return accountTree.toSortedList();
    }
    
    /**
     * Gets the total number of accounts.
     *
     * @return account count
     */
    public int getAccountCount() {
        return accountTree.size();
    }
    
    /**
     * Calculates total balance across all accounts.
     *
     * @return total balance
     */
    public Money calculateTotalBalance() {
        Money total = Money.zero("EUR");
        
        for (Account account : accountTree.toSortedList()) {
            total = total.add(account.getBalance());
        }
        
        return total;
    }
    
    /**
     * Finds accounts with balance above threshold.
     *
     * @param threshold minimum balance
     * @return list of qualifying accounts
     */
    public List<Account> findAccountsAboveBalance(Money threshold) {
        Objects.requireNonNull(threshold, "Threshold cannot be null");
        
        return accountTree.toSortedList().stream()
            .filter(account -> account.getBalance().isGreaterThan(threshold))
            .toList();
    }
    
    /**
     * Checks if an account exists.
     *
     * @param accountNumber the account number
     * @return true if account exists
     */
    public boolean accountExists(String accountNumber) {
        return findAccount(accountNumber) != null;
    }
    
    /**
     * Clears all accounts (for testing purposes).
     */
    public void clearAllAccounts() {
        accountTree.clear();
    }
}