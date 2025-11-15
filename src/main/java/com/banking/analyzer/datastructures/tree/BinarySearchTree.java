package com.banking.analyzer.datastructures.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Generic Binary Search Tree implementation for efficient account storage and retrieval.
 *
 * @param <T> the type of comparable elements stored in this tree
 */
public class BinarySearchTree<T extends Comparable<T>> {
    
    private Node<T> root;
    private int size;
    
    /**
     * Inner class representing a node in the binary search tree.
     */
    private static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        
        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }
    
    /**
     * Inserts an element into the tree maintaining BST property.
     */
    public void insert(T element) {
        Objects.requireNonNull(element, "Cannot insert null element");
        root = insertRecursive(root, element);
        size++;
    }
    
    /**
     * Recursive helper method for insertion.
     * Demonstrates recursion requirement.
     */
    private Node<T> insertRecursive(Node<T> node, T element) {
        if (node == null) {
            return new Node<>(element);
        }
        
        int comparison = element.compareTo(node.data);
        
        if (comparison < 0) {
            node.left = insertRecursive(node.left, element);
        } else if (comparison > 0) {
            node.right = insertRecursive(node.right, element);
        }
        // Equal elements are not inserted (no duplicates)
        
        return node;
    }
    
    /**
     * Searches for an element in the tree.
     */
    public boolean contains(T element) {
        Objects.requireNonNull(element, "Search element cannot be null");
        return containsRecursive(root, element);
    }
    
    /**
     * Recursive helper method for search.
     */
    private boolean containsRecursive(Node<T> node, T element) {
        if (node == null) {
            return false;
        }
        
        int comparison = element.compareTo(node.data);
        
        if (comparison == 0) {
            return true;
        } else if (comparison < 0) {
            return containsRecursive(node.left, element);
        } else {
            return containsRecursive(node.right, element);
        }
    }
    
    public T find(T element) {
        Objects.requireNonNull(element, "Search element cannot be null");
        return findRecursive(root, element);
    }
    
    /**
     * Recursive helper method to find an element.
     */
    private T findRecursive(Node<T> node, T element) {
        if (node == null) {
            return null;
        }
        
        int comparison = element.compareTo(node.data);
        
        if (comparison == 0) {
            return node.data;
        } else if (comparison < 0) {
            return findRecursive(node.left, element);
        } else {
            return findRecursive(node.right, element);
        }
    }
    
    /**
     * Removes an element from the tree.
     */
    public boolean remove(T element) {
        Objects.requireNonNull(element, "Cannot remove null element");
        
        if (!contains(element)) {
            return false;
        }
        
        root = removeRecursive(root, element);
        size--;
        return true;
    }
    
    /**
     * Recursive helper method for deletion.
     */
    private Node<T> removeRecursive(Node<T> node, T element) {
        if (node == null) {
            return null;
        }
        
        int comparison = element.compareTo(node.data);
        
        if (comparison < 0) {
            node.left = removeRecursive(node.left, element);
        } else if (comparison > 0) {
            node.right = removeRecursive(node.right, element);
        } else {
            
            // Case 1: Leaf node
            if (node.left == null && node.right == null) {
                return null;
            }
            
            // Case 2: One child
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            
            // Case 3: Two children
            T minValue = findMinRecursive(node.right);
            node.data = minValue;
            node.right = removeRecursive(node.right, minValue);
        }
        
        return node;
    }
    
    /**
     * Finds the minimum element in a subtree.
     */
    private T findMinRecursive(Node<T> node) {
        return node.left == null ? node.data : findMinRecursive(node.left);
    }
    
    /**
     * Finds the maximum element in a subtree.
     */
    private T findMaxRecursive(Node<T> node) {
        return node.right == null ? node.data : findMaxRecursive(node.right);
    }
    
    /**
     * Returns the minimum element in the tree.
     */
    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return findMinRecursive(root);
    }
    
    /**
     * Returns the maximum element in the tree.
     */
    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        return findMaxRecursive(root);
    }
    
    public void inOrderTraversal(Consumer<T> action) {
        Objects.requireNonNull(action, "Action cannot be null");
        inOrderRecursive(root, action);
    }
    
    /**
     * Recursive helper for in-order traversal.
     */
    private void inOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            inOrderRecursive(node.left, action);
            action.accept(node.data);
            inOrderRecursive(node.right, action);
        }
    }
    
    /**
     * Performs pre-order traversal (root-left-right).
     */
    public void preOrderTraversal(Consumer<T> action) {
        Objects.requireNonNull(action, "Action cannot be null");
        preOrderRecursive(root, action);
    }
    
    /**
     * Recursive helper for pre-order traversal.
     */
    private void preOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            action.accept(node.data);
            preOrderRecursive(node.left, action);
            preOrderRecursive(node.right, action);
        }
    }
    
    /**
     * Performs post-order traversal (left-right-root).
     */
    public void postOrderTraversal(Consumer<T> action) {
        Objects.requireNonNull(action, "Action cannot be null");
        postOrderRecursive(root, action);
    }
    
    /**
     * Recursive helper for post-order traversal.
     */
    private void postOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node != null) {
            postOrderRecursive(node.left, action);
            postOrderRecursive(node.right, action);
            action.accept(node.data);
        }
    }
    
    /**
     * Returns all elements in sorted order.
     */
    public List<T> toSortedList() {
        List<T> list = new ArrayList<>();
        inOrderTraversal(list::add);
        return list;
    }
    
    /**
     * Calculates the height of the tree.
     */
    public int height() {
        return heightRecursive(root);
    }
    
    /**
     * Recursive helper to calculate height.
     */
    private int heightRecursive(Node<T> node) {
        if (node == null) {
            return -1;
        }
        
        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    

    public int size() {
        return size;
    }
    

    public boolean isEmpty() {
        return size == 0;
    }
    

    public void clear() {
        root = null;
        size = 0;
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "BST[]";
        }
        
        StringBuilder sb = new StringBuilder("BST[");
        List<T> elements = toSortedList();
        
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i));
            if (i < elements.size() - 1) {
                sb.append(", ");
            }
        }
        
        sb.append("]");
        return sb.toString();
    }
}