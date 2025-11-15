package com.banking.analyzer.datastructures.tree;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeTest {
    
    public static void main(String[] args) {
        BinarySearchTreeTest test = new BinarySearchTreeTest();
        test.runAllTests();
    }
    
    public void runAllTests() {
        System.out.println("Running BinarySearchTree Tests...\n");
        
        testInsertAndContains();
        testRemove();
        testTraversals();
        testMinMax();
        
        System.out.println("\n✓ All BinarySearchTree tests passed!");
    }
    
    private void testInsertAndContains() {
        System.out.println("Test: Insert and Contains");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        
        assertTrue(tree.contains(50), "Should contain 50");
        assertTrue(tree.contains(30), "Should contain 30");
        assertEqual(3, tree.size(), "Size should be 3");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testRemove() {
        System.out.println("Test: Remove");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        
        boolean removed = tree.remove(30);
        assertTrue(removed, "Remove should return true");
        assertEqual(2, tree.size(), "Size should be 2");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testTraversals() {
        System.out.println("Test: In-Order Traversal");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        
        List<Integer> result = new ArrayList<>();
        tree.inOrderTraversal(result::add);
        
        assertEqual((Object)30, (Object)result.get(0), "First should be 30");
        assertEqual((Object)50, (Object)result.get(1), "Second should be 50");
        assertEqual((Object)70, (Object)result.get(2), "Third should be 70");
        System.out.println("  ✓ Passed\n");
    }
    
    private void testMinMax() {
        System.out.println("Test: Min/Max");
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        
        assertEqual((Object)30, (Object)tree.findMin(), "Min should be 30");
        assertEqual((Object)70, (Object)tree.findMax(), "Max should be 70");
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