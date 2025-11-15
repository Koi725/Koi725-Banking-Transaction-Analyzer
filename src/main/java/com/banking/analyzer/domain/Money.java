package com.banking.analyzer.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Immutable value object representing monetary amounts.
 */
public final class Money {
    private static final int DECIMAL_PLACES = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    
    private final BigDecimal amount;
    private final String currency;
    
    private Money(BigDecimal amount, String currency) {
        this.amount = amount.setScale(DECIMAL_PLACES, ROUNDING_MODE);
        this.currency = currency;
    }
    
    public static Money of(double amount, String currency) {
        validateCurrency(currency);
        return new Money(BigDecimal.valueOf(amount), currency);
    }
    
    public static Money of(BigDecimal amount, String currency) {
        validateCurrency(currency);
        Objects.requireNonNull(amount, "Amount cannot be null");
        return new Money(amount, currency);
    }
    
    public static Money zero(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }
    
    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }
    
    public Money multiply(double multiplier) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)), this.currency);
    }
    
    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
    
    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }
    
    public boolean isLessThan(Money other) {
        validateSameCurrency(other);
        return this.amount.compareTo(other.amount) < 0;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                String.format("Cannot operate on different currencies: %s and %s", 
                    this.currency, other.currency)
            );
        }
    }
    
    private static void validateCurrency(String currency) {
        Objects.requireNonNull(currency, "Currency cannot be null");
        if (currency.trim().isEmpty() || currency.length() != 3) {
            throw new IllegalArgumentException("Invalid currency code: " + currency);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return amount.compareTo(money.amount) == 0 && currency.equals(money.currency);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
    
    @Override
    public String toString() {
        return String.format("%s %.2f", currency, amount);
    }
}