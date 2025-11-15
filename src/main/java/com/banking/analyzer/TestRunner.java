package com.banking.analyzer;

import com.banking.analyzer.core.domain.MoneyTest;
import com.banking.analyzer.datastructures.list.CustomLinkedListTest;
import com.banking.analyzer.datastructures.tree.BinarySearchTreeTest;

public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("                    BANKING TRANSACTION ANALYZER");
        System.out.println("                         UNIT TEST SUITE");
        System.out.println("================================================================================\n");
        
        int passed = 0;
        int failed = 0;
        
        try {
            new MoneyTest().runAllTests();
            passed++;
            
            new CustomLinkedListTest().runAllTests();
            passed++;
            
            new BinarySearchTreeTest().runAllTests();
            passed++;
            
        } catch (AssertionError e) {
            failed++;
            System.err.println("\n✗ TEST FAILED: " + e.getMessage());
        }
        
        System.out.println("\n================================================================================");
        System.out.println("RESULTS: Passed: " + passed + " | Failed: " + failed);
        System.out.println("================================================================================\n");
    }
}