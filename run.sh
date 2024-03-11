#!/bin/bash

# Compile the Java files
javac src/main/*.java -d classes

# Run compiled Java files
if [ $? -eq 0 ]; then
    java -cp classes main.Main
else
    echo "Compilation failed. Please check for errors."
fi

read -p "Press Enter to exit..."
