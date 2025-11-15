# Quick Start Guide

Get the Banking Transaction Analyzer up and running in under 2 minutes.

---

## Prerequisites

- Java JDK 11 or higher
- Bash shell (Linux/Mac) or Git Bash (Windows)

---

## Installation

```bash
# Clone the repository
git clone <repository-url>
cd banking-transaction-analyzer
```

---

## Running the Project

### Option 1: Automated Build (Recommended)

```bash
# Make script executable
chmod +x build.sh

# Run everything (compile + test + demo)
./build.sh all
```

### Option 2: Individual Commands

```bash
# Just compile
./build.sh compile

# Run tests only
./build.sh test

# Run application only
./build.sh run

# Clean build artifacts
./build.sh clean
```

### Option 3: Manual Compilation

```bash
# Compile source files
find src/main/java -name "*.java" > sources.txt
javac -d build/classes -sourcepath src/main/java @sources.txt

# Run the application
java -cp build/classes com.banking.analyzer.Application

# Clean up
rm sources.txt
```

---

## What to Expect

When you run the application, you'll see:

1. **Account Management Demo** - Binary Search Tree in action
2. **Transaction Processing** - Custom Linked List operations
3. **Recursive Analysis** - Various recursive algorithms
4. **Fraud Detection** - Pattern matching and alerts
5. **Statistics Summary** - Complete system overview

---

## Project Structure

```
banking-transaction-analyzer/
├── src/main/java/
│   └── com/banking/analyzer/
│       ├── Application.java          # Main entry point
│       ├── core/
│       │   ├── domain/              # Business entities
│       │   └── service/             # Business logic
│       ├── datastructures/
│       │   ├── list/                # Custom LinkedList
│       │   ├── tree/                # Binary Search Tree
│       │   └── recursion/           # Recursive algorithms
│       └── util/                    # Helper classes
├── build.sh                         # Build automation script
└── README.md                        # Project documentation
```

---

## Troubleshooting

### Build script won't execute

```bash
chmod +x build.sh
```

### "javac: command not found"

Install JDK:

- **Mac**: `brew install openjdk@11`
- **Linux**: `sudo apt-get install default-jdk`
- **Windows**: Download from [oracle.com/java](https://oracle.com/java)

### Compilation errors

```bash
./build.sh clean
./build.sh compile
```

---

## Requirements Covered

- **Recursion**: 9 different recursive algorithms implemented
- **Lists**: Custom doubly-linked list from scratch
- **Trees**: Complete Binary Search Tree implementation

---
