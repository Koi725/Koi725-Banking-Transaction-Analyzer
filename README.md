# Banking Transaction Analyzer

A Java application that processes banking transactions, manages accounts, and detects fraud patterns. Built from scratch to learn data structures and algorithms.

## What This Does

This project simulates a banking system that:

- Stores accounts in a searchable tree structure
- Tracks transactions in chronological order
- Analyzes spending patterns
- Flags suspicious activity

Everything is built without using Java's built-in Collections. All data structures are custom implementations.

## Technical Stuff

### What's Inside

**Custom Data Structures:**

- Doubly-linked list for transaction history
- Binary search tree for account management
- Nine different recursive algorithms

**Design Patterns:**

- Builder pattern for creating complex objects
- Iterator pattern for looping through data
- Immutable objects where it makes sense

**Best Practices:**

- Everything is properly encapsulated
- No code smells or anti-patterns
- Clean separation between business logic and data structures

## How to Run

### Quick Start

```bash
chmod +x build.sh
./build.sh all
```

That's it. The script compiles everything, runs tests, and shows you a demo.

### Manual Way

If you prefer doing it yourself:

```bash
# Compile
javac -d build/classes -sourcepath src/main/java src/main/java/com/banking/analyzer/Application.java

# Run
java -cp build/classes com.banking.analyzer.Application
```

## What You'll See

The demo creates some accounts, processes transactions, and shows various operations:

1. Creating accounts and inserting them into a binary search tree
2. Processing transactions with a custom linked list
3. Running recursive analysis (totals, averages, max values)
4. Detecting fraud patterns
5. Displaying statistics

It's all automated, so you just run it and watch.

## Project Structure

```
src/main/java/com/banking/analyzer/
├── Application.java              # Runs the demo
├── core/
│   ├── domain/                   # Account, Transaction, Money classes
│   └── service/                  # Business logic
├── datastructures/
│   ├── list/                     # Custom linked list
│   ├── tree/                     # Binary search tree
│   └── recursion/                # Recursive algorithms
└── util/                         # Helper stuff
```

## Key Features

### 1. Custom Linked List

Built a doubly-linked list that:

- Adds items in O(1) time at both ends
- Iterates forward and backward
- Filters and searches through data

No ArrayList or LinkedList from Java Collections. All manual pointer management.

### 2. Binary Search Tree

Implemented a BST that:

- Searches in O(log n) time on average
- Handles all three deletion cases properly
- Does in-order, pre-order, and post-order traversals

Stores accounts sorted by account number for fast lookups.

### 3. Recursive Algorithms

Nine different recursive functions including:

- Basic recursion (sum, average)
- Divide and conquer (finding max)
- Backtracking (pattern detection)
- Tree traversals

### 4. Fraud Detection

Flags suspicious transactions based on:

- Large amounts (over €10,000)
- Weird timing (late night withdrawals)
- Rapid sequences (many transactions in short time)

Uses recursive pattern matching to detect these.

## Testing

Includes unit tests for all major components:

- Money calculations and currency handling
- Linked list operations
- Binary search tree functionality

Run tests with: `./build.sh test`

## Things I Learned

**Data Structures Matter**: Using the right structure makes a huge difference. Searching through a BST is way faster than searching through an unsorted list.

**Recursion Is Powerful**: Once you understand it, recursion makes complex problems simpler. Tree traversals are incredibly clean with recursion.

**Encapsulation Prevents Bugs**: Keeping data private and controlling access through methods caught so many bugs before they happened.

**Testing Saves Time**: Writing tests felt slow at first, but it saved hours of debugging later.

## Technical Decisions

### Why Doubly-Linked Instead of Singly-Linked?

Need to remove from both ends in O(1) time. Single-linked lists can only do this from the front.

### Why BST Instead of Hash Table?

BST keeps accounts sorted, which is useful for generating reports. Also wanted to practice tree operations.

### Why BigDecimal for Money?

Floating-point numbers are terrible for currency. BigDecimal prevents rounding errors.

### Why Immutable Objects?

Money and Transaction are immutable to prevent accidental changes. Once created, they can't be modified. Safer and easier to reason about.

## Requirements Met

This was built for a data structures course. Requirements were:

- Implement custom linked list
- Implement binary search tree
- Use recursion extensively

All three are covered thoroughly with real applications, not just toy examples.

## Future Ideas

If I expand this project:

- Add database persistence
- Build a REST API
- Create a web interface
- Implement more sophisticated fraud detection
- Add support for multiple currencies

## Building From Source

Requires Java 11 or newer. The build script handles everything:

```bash
./build.sh compile    # Just compile
./build.sh test       # Run tests
./build.sh run        # Run demo
./build.sh all        # Everything
./build.sh clean      # Remove build files
```

## Contributing

This is a learning project, but if you find bugs or have suggestions, feel free to open an issue.

## License

MIT - use it however you want for learning.

## Acknowledgments

Built as a learning exercise to understand data structures at a deeper level.
