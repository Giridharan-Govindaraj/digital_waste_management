#!/bin/bash


RED='\033[0;31m'
GREEN='\033[0;32m'
RESET='\033[0m'

# Source the .bash_profile to ensure environment variables are set

executeBashProfile(){
    if [ -f ~/.bash_profile ]; then
    echo "Sourcing .bash_profile..."
    source ~/.bash_profile
    else
        echo ".bash_profile not found. Skipping sourcing."
    fi
    
}


isJavaInstalled(){

    # Check if Java is installed
    if command -v java &>/dev/null; then
        # Java is installed, print the version
        echo "Java is installed. Version details:"
        java -version
    else
        # Java is not installed
        echo "ERROR: Java is not installed on your system. Please install required java on your machine!!"
        exit 1
    fi


}

setQBFT_HOME(){
    export QBFT_HOME=$(pwd)
    chmod 777 -R ./
    echo "Setting current directory as a QBFT_HOME: '$QBFT_HOME'"

    besu operator generate-blockchain-config --config-file=$QBFT_HOME/config/qbftConfigFile.json --to=$QBFT_HOME/networkFiles --private-key-file-name=key

}



main(){

    echo "Starting Script.............................................."
    executeBashProfile
    echo
    isJavaInstalled
    echo
    setQBFT_HOME
    echo
    
    echo "Script executed successfully!!!"
    
    exit 0
}


##Running main function
main



