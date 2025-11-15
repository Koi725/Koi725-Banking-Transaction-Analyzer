#!/bin/bash

echo "================================================================================"
echo "               BANKING TRANSACTION ANALYZER - BUILD SCRIPT"
echo "================================================================================"

SRC_DIR="src/main/java"
TEST_SRC_DIR="src/main/java"
BUILD_DIR="build/classes"
TEST_BUILD_DIR="build/test-classes"

clean() {
    echo "Cleaning..."
    rm -rf build
    mkdir -p $BUILD_DIR $TEST_BUILD_DIR
    echo "✓ Clean complete"
}

compile() {
    echo "Compiling main source..."
    find $SRC_DIR -name "*.java" > sources.txt
    javac -d $BUILD_DIR -sourcepath $SRC_DIR @sources.txt
    if [ $? -eq 0 ]; then
        echo "✓ Compilation successful"
    else
        echo "✗ Compilation failed"
        exit 1
    fi
    rm sources.txt
}

compile_tests() {
    echo "Compiling tests..."
    find $TEST_SRC_DIR -name "*.java" > test-sources.txt
    javac -d $TEST_BUILD_DIR -cp $BUILD_DIR -sourcepath $TEST_SRC_DIR @test-sources.txt
    if [ $? -eq 0 ]; then
        echo "✓ Test compilation successful"
    else
        echo "✗ Test compilation failed"
        exit 1
    fi
    rm test-sources.txt
}

run_tests() {
    echo "Running tests..."
    java -cp $BUILD_DIR:$TEST_BUILD_DIR com.banking.analyzer.TestRunner
}

run() {
    echo "Running application..."
    java -cp $BUILD_DIR com.banking.analyzer.Application
}

case "$1" in
    clean) clean ;;
    compile) clean; compile ;;
    test) clean; compile; compile_tests; run_tests ;;
    run) clean; compile; run ;;
    all) clean; compile; compile_tests; run_tests; run ;;
    *)
        echo "Usage: $0 {clean|compile|test|run|all}"
        exit 1
        ;;
esac

echo "================================================================================"