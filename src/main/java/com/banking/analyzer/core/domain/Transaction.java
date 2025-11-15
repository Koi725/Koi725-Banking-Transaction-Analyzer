package com.banking.analyzer.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a banking transaction with complete business rules encapsulation.
 */
public final class Transaction {
    private final String transactionId;
    private final String accountId;
    private final TransactionType type;
    private final Money amount;
    private final LocalDateTime timestamp;
    private final String description;
    private final String category;
    private final String merchantName;
    
    private Transaction(Builder builder) {
        this.transactionId = builder.transactionId;
        this.accountId = builder.accountId;
        this.type = builder.type;
        this.amount = builder.amount;
        this.timestamp = builder.timestamp;
        this.description = builder.description;
        this.category = builder.category;
        this.merchantName = builder.merchantName;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public Money getAmount() {
        return amount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public String getMerchantName() {
        return merchantName;
    }
    
    public boolean isDebit() {
        return type.isDebit();
    }
    
    public boolean isCredit() {
        return type.isCredit();
    }
    
    public boolean occurredAfter(LocalDateTime dateTime) {
        return timestamp.isAfter(dateTime);
    }
    
    public boolean occurredBefore(LocalDateTime dateTime) {
        return timestamp.isBefore(dateTime);
    }
    
    public boolean isSuspicious() {
        // Basic fraud detection rules
        return amount.isGreaterThan(Money.of(10000.0, amount.getCurrency())) ||
               (type == TransactionType.WITHDRAWAL && 
                timestamp.getHour() >= 0 && timestamp.getHour() <= 4);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return transactionId.equals(that.transactionId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
    
    @Override
    public String toString() {
        return String.format("Transaction[id=%s, type=%s, amount=%s, timestamp=%s]",
            transactionId, type, amount, timestamp);
    }
    public static class Builder {
        private String transactionId;
        private String accountId;
        private TransactionType type;
        private Money amount;
        private LocalDateTime timestamp;
        private String description;
        private String category;
        private String merchantName;
        
        private Builder() {
            this.transactionId = UUID.randomUUID().toString();
            this.timestamp = LocalDateTime.now();
        }
        
        public Builder transactionId(String transactionId) {
            this.transactionId = Objects.requireNonNull(transactionId, "Transaction ID cannot be null");
            return this;
        }
        
        public Builder accountId(String accountId) {
            this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
            return this;
        }
        
        public Builder type(TransactionType type) {
            this.type = Objects.requireNonNull(type, "Transaction type cannot be null");
            return this;
        }
        
        public Builder amount(Money amount) {
            this.amount = Objects.requireNonNull(amount, "Amount cannot be null");
            return this;
        }
        
        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = Objects.requireNonNull(timestamp, "Timestamp cannot be null");
            return this;
        }
        
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        
        public Builder category(String category) {
            this.category = category;
            return this;
        }
        
        public Builder merchantName(String merchantName) {
            this.merchantName = merchantName;
            return this;
        }
        
        public Transaction build() {
            validateRequiredFields();
            return new Transaction(this);
        }
        
        private void validateRequiredFields() {
            Objects.requireNonNull(accountId, "Account ID is required");
            Objects.requireNonNull(type, "Transaction type is required");
            Objects.requireNonNull(amount, "Amount is required");
        }
    }
}