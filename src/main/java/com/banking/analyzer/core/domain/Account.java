package com.banking.analyzer.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a bank account with hierarchical relationships.
 */
public class Account implements Comparable<Account> {
    private final String accountId;
    private final String accountNumber;
    private final String accountHolderName;
    private final AccountType type;
    private final LocalDateTime createdAt;
    private Money balance;
    private boolean active;
    
    public enum AccountType {
        PERSONAL,
        BUSINESS,
        CORPORATE,
        SAVINGS,
        CHECKING
    }
    
    private Account(Builder builder) {
        this.accountId = builder.accountId;
        this.accountNumber = builder.accountNumber;
        this.accountHolderName = builder.accountHolderName;
        this.type = builder.type;
        this.createdAt = builder.createdAt;
        this.balance = builder.balance;
        this.active = builder.active;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public void deposit(Money amount) {
        if (!active) {
            throw new IllegalStateException("Account is not active");
        }
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }
    
    public void withdraw(Money amount) {
        if (!active) {
            throw new IllegalStateException("Account is not active");
        }
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (balance.isLessThan(amount)) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance = this.balance.subtract(amount);
    }
    
    public void deactivate() {
        this.active = false;
    }
    
    public void activate() {
        this.active = true;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getAccountHolderName() {
        return accountHolderName;
    }
    
    public AccountType getType() {
        return type;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public Money getBalance() {
        return balance;
    }
    
    public boolean isActive() {
        return active;
    }
    
    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return accountId.equals(account.accountId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accountId);
    }
    
    @Override
    public String toString() {
        return String.format("Account[number=%s, holder=%s, balance=%s, active=%s]",
            accountNumber, accountHolderName, balance, active);
    }
    
    public static class Builder {
        private String accountId;
        private String accountNumber;
        private String accountHolderName;
        private AccountType type;
        private LocalDateTime createdAt;
        private Money balance;
        private boolean active;
        
        private Builder() {
            this.createdAt = LocalDateTime.now();
            this.balance = Money.zero("EUR");
            this.active = true;
        }
        
        public Builder accountId(String accountId) {
            this.accountId = Objects.requireNonNull(accountId);
            return this;
        }
        
        public Builder accountNumber(String accountNumber) {
            this.accountNumber = Objects.requireNonNull(accountNumber);
            return this;
        }
        
        public Builder accountHolderName(String accountHolderName) {
            this.accountHolderName = Objects.requireNonNull(accountHolderName);
            return this;
        }
        
        public Builder type(AccountType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }
        
        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = Objects.requireNonNull(createdAt);
            return this;
        }
        
        public Builder balance(Money balance) {
            this.balance = Objects.requireNonNull(balance);
            return this;
        }
        
        public Builder active(boolean active) {
            this.active = active;
            return this;
        }
        
        public Account build() {
            validateRequiredFields();
            return new Account(this);
        }
        
        private void validateRequiredFields() {
            Objects.requireNonNull(accountId, "Account ID is required");
            Objects.requireNonNull(accountNumber, "Account number is required");
            Objects.requireNonNull(accountHolderName, "Account holder name is required");
            Objects.requireNonNull(type, "Account type is required");
        }
    }
}