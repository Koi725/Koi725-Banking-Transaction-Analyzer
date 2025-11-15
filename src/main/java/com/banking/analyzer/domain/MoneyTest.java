package com.banking.analyzer.core.domain;

public class MoneyTest {
    
    public static void main(String[] args) {
        MoneyTest test = new MoneyTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running Money Tests...\n");
        
        testMoneyCreation();
        testMoneyAddition();
        testMoneyComparison();
        
        System.out.println("\n✓ All Money tests passed!");
    }
    
    private void testMoneyCreation() {
        System.out.println("Test: Money Creation");
        Money money = Money.of(100.0, "EUR");
        assertEqual("EUR", money.getCurrency(), "Currency should be EUR");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testMoneyAddition() {
        System.out.println("Test: Money Addition");
        Money m1 = Money.of(100.0, "EUR");
        Money m2 = Money.of(50.0, "EUR");
        Money result = m1.add(m2);
        assertEqual(150.0, result.getAmount().doubleValue(), "100 + 50 = 150");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testMoneyComparison() {
        System.out.println("Test: Money Comparison");
        Money m1 = Money.of(100.0, "EUR");
        Money m2 = Money.of(50.0, "EUR");
        assertTrue(m1.isGreaterThan(m2), "100 > 50");
        System.out.println("  ✓ Passed\n");
    }
    
    private void assertEqual(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message);
        }
    }
    
    private void assertEqual(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.001) {
            throw new AssertionError(message);
        }
    }
    
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}