package com.banking.analyzer.core.domain;

/**
 * Enumeration of supported transaction types.
 */
public enum TransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal"),
    TRANSFER("Transfer"),
    PAYMENT("Payment"),
    REFUND("Refund"),
    FEE("Fee");
    
    private final String displayName;
    
    TransactionType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isDebit() {
        return this == WITHDRAWAL || this == TRANSFER || this == PAYMENT || this == FEE;
    }
    
    public boolean isCredit() {
        return this == DEPOSIT || this == REFUND;
    }
}