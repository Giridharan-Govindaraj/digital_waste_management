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

   
}

generateKeyPairs(){
    echo "Generating node key pairs"

    besu operator generate-blockchain-config --config-file=$QBFT_HOME/config/qbftConfigFile.json --to=$QBFT_HOME/networkFiles --private-key-file-name=key


}

copyGenesisFile(){


    # Check if genesis.json already exists in the network directory
    if [ -f "$QBFT_HOME/qbft_network/genesis.json" ]; then
    echo "genesis.json already exists in the network directory. Deleting it."
    rm "$QBFT_HOME/qbft_network/genesis.json"
    fi

    echo "Copying genesis.json to the network directory."
    cp "$QBFT_HOME/networkFiles/genesis.json" "$QBFT_HOME/qbft_network/"
    echo "Copied genesis.json to $QBFT_HOME/qbft_network/"

}
copyKeyPairs(){

    #!/bin/bash
    echo "Copying node KeyPairs"
    # List all directories in network_files/keys
    # dirs=($QBFT_HOME/networkFiles/keys/*)

node_counter=1
    for dir in $QBFT_HOME/networkFiles/keys/*; do
    # Ensure we don't exceed the available node directories
    if [ $node_counter -le 4 ]; then
        # Copy contents of the current hex directory to the corresponding node directory
        rm -rf "$QBFT_HOME/qbft_network/node$node_counter/"*

        mkdir -p $QBFT_HOME/qbft_network/node$node_counter/data && mkdir -p $QBFT_HOME/qbft_network/node$node_counter/logs
        cp -r "$dir/"* "$QBFT_HOME/qbft_network/node$node_counter/data/"
        echo "Copied from $dir to $QBFT_HOME/qbft_network/node$node_counter/data"
        node_counter=$((node_counter + 1)) # Increment the counter for the next node
    else
        echo "Warning: More than 4 directories found in network_files/keys. Skipping remaining."
        break
    fi
    done

}


main(){

    echo "Starting Script.............................................."
    executeBashProfile
    echo
    isJavaInstalled
    echo
    setQBFT_HOME
    echo
    generateKeyPairs
    echo
    copyGenesisFile
    echo
    copyKeyPairs
    echo "Script executed successfully!!!"
    
    exit 0
}


##Running main function
main



