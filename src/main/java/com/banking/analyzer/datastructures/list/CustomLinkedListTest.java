package com.banking.analyzer.datastructures.list;

public class CustomLinkedListTest {
    
    public static void main(String[] args) {
        CustomLinkedListTest test = new CustomLinkedListTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running CustomLinkedList Tests...\n");
        
        testAddAndSize();
        testGet();
        testRemove();
        testContains();
        
        System.out.println("\n✓ All CustomLinkedList tests passed!");
    }
    
    private void testAddAndSize() {
        System.out.println("Test: Add and Size");
        CustomLinkedList<String> list = new CustomLinkedList<>();
        assertTrue(list.isEmpty(), "New list should be empty");
        
        list.add("First");
        assertEqual(1, list.size(), "Size should be 1");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testGet() {
        System.out.println("Test: Get Element");
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(10);
        list.add(20);
        
        assertEqual((Object)10, (Object)list.get(0), "Element at 0 should be 10");
        assertEqual((Object)20, (Object)list.get(1), "Element at 1 should be 20");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testRemove() {
        System.out.println("Test: Remove");
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("A");
        list.add("B");
        
        boolean removed = list.remove("A");
        assertTrue(removed, "Remove should return true");
        assertEqual(1, list.size(), "Size should be 1");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testContains() {
        System.out.println("Test: Contains");
        CustomLinkedList<String> list = new CustomLinkedList<>();
        list.add("Apple");
        
        assertTrue(list.contains("Apple"), "Should contain Apple");
        System.out.println("  ✓ Passed\n");
    }
    
    private void assertEqual(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message);
        }
    }
    
    private void assertEqual(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message);
        }
    }
    
    private void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}