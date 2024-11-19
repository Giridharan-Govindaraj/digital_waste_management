#!/bin/bash

# Source the .bash_profile to ensure environment variables are set
if [ -f ~/.bash_profile ]; then
    echo "Sourcing .bash_profile..."
    source ~/.bash_profile
else
    echo ".bash_profile not found. Skipping sourcing."
fi

# Check if Java is installed
if command -v java &>/dev/null; then
    # Java is installed, print the version
    echo "Java is installed. Version details:"
    java -version
else
    # Java is not installed
    echo "Java is not installed on your system."
fi
