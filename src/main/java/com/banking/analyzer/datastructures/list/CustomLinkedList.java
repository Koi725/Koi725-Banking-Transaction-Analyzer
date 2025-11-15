package com.banking.analyzer.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Custom doubly-linked list implementation demonstrating fundamental data structure concepts.
 * Provides O(1) insertion at both ends and O(n) search operations.
 *
 * @param <T> the type of elements stored in this list
 */
public class CustomLinkedList<T> implements Iterable<T> {
    
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    /**
     * Inner class representing a node in the doubly-linked list.
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> previous;
        
        Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }
    
    public CustomLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
    
    /**
     * Time complexity: O(1)
     */
    public void add(T element) {
        Objects.requireNonNull(element, "Cannot add null element");
        
        Node<T> newNode = new Node<>(element);
        
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        
        size++;
    }
    
    /**
     * Adds an element to the beginning of the list.
     */
    public void addFirst(T element) {
        Objects.requireNonNull(element, "Cannot add null element");
        
        Node<T> newNode = new Node<>(element);
        
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        
        size++;
    }
    
    /**
     * Retrieves the element at the specified index.
     */
    public T get(int index) {
        validateIndex(index);
        return getNodeAt(index).data;
    }
    
    /**
     * Removes and returns the first element.
     */
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        
        T data = head.data;
        
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        
        size--;
        return data;
    }
    
    /**
     * Removes and returns the last element.
     */
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        
        T data = tail.data;
        
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }
        
        size--;
        return data;
    }
    
    /**
     * Removes the first occurrence of the specified element.
     */
    public boolean remove(T element) {
        Node<T> current = head;
        
        while (current != null) {
            if (current.data.equals(element)) {
                removeNode(current);
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Filters the list based on a predicate and returns a new list.
     */
    public CustomLinkedList<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "Predicate cannot be null");
        
        CustomLinkedList<T> filteredList = new CustomLinkedList<>();
        
        for (T element : this) {
            if (predicate.test(element)) {
                filteredList.add(element);
            }
        }
        
        return filteredList;
    }
    
    /**
     * Applies an action to each element in the list.
     */
    public void forEachElement(Consumer<T> action) {
        Objects.requireNonNull(action, "Action cannot be null");
        
        Node<T> current = head;
        while (current != null) {
            action.accept(current.data);
            current = current.next;
        }
    }
    
    /**
     * Checks if the list contains the specified element.
     */
    public boolean contains(T element) {
        Node<T> current = head;
        
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Returns the number of elements in the list.
     */
    public int size() {
        return size;
    }
    
    /**
     * Checks if the list is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Removes all elements from the list.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
    
    /**
     * Returns an array containing all elements in the list.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        Node<T> current = head;
        int index = 0;
        
        while (current != null) {
            array[index++] = current.data;
            current = current.next;
        }
        
        return array;
    }
    
    private Node<T> getNodeAt(int index) {
        Node<T> current;
        
        // Optimize by starting from the closer end
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        
        return current;
    }
    
    private void removeNode(Node<T> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }
        
        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }
        
        size--;
    }
    
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                String.format("Index %d out of bounds for size %d", index, size)
            );
        }
    }
    
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }
    
    /**
     * Custom iterator implementation for the linked list.
     */
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            T data = current.data;
            current = current.next;
            return data;
        }
    }
    
    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        
        while (current != null) {
            sb.append(current.data);
            if (current.next != null) {
                sb.append(", ");
            }
            current = current.next;
        }
        
        sb.append("]");
        return sb.toString();
    }
}