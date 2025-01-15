


exports(){
    export QBFT_HOME=$(pwd)
    export DIAMOND=contract/diamond/Diamond.sol
    export DIAMOND_INIT=contract/diamond/upgradeInitializers/DiamondInit.sol
    export DIAMOND_CUT_FACET=contract/diamond/facets/DiamondCutFacet.sol
    export DIAMOND_LOUPE_FACET=contract/diamond/facets/DiamondLoupeFacet.sol
    export OWNERSHIP_FACET=contract/diamond/facets/OwnershipFacet.sol    
    export BIN=contract/diamond/bin
    export RESOURCE_PATH=BesuApp/src/main/resources/contracts/diamond

    mkdir -p $BIN
    echo "Setting current directory as a QBFT_HOME: '$QBFT_HOME'"

   
}

isSolcInstalled(){
     # Check if Java is installed
    if command -v solc &>/dev/null; then
        # Java is installed, print the version
        echo "solc is installed. Version details:"
        solc --version
        
    else
        # Java is not installed
        echo "ERROR: solc is not installed on your system. Please install required solc on your machine!!"
        exit 1
    fi
}


isContractsAvailable(){
    if [ -f "$DIAMOND" ]; then
        echo "Diamond contract file exists"
    else
        echo "Diamond contract is not present"
        exit 1
    fi

    if [ -f "$DIAMOND_INIT" ]; then
        echo "DiamondInit contract file exists"
    else
        echo "DiamondInit contract is not present"
        exit 1
    fi

    if [ -f "$DIAMOND_CUT_FACET" ]; then
        echo "DiamondCutFacet contract file exists"
    else
        echo "DiamondCutFacet contract is not present"
        exit 1
    fi

    if [ -f "$DIAMOND_LOUPE_FACET" ]; then
        echo "DiamondLoupeFacet contract file exists"
    else
        echo "DiamondLoupeFacet contract is not present"
        exit 1
    fi

    if [ -f "$OWNERSHIP_FACET" ]; then
        echo "OwnershipFacet contract file exists"
    else
        echo "OwnershipFacet contract is not present"
        exit 1
    fi

}


compileDiamondContract() {
    NAME="Diamond"    
    # Compile the contract and save to a temporary file
    solc --optimize --evm-version london --color --pretty-json --combined-json abi,bin,hashes $QBFT_HOME/$DIAMOND > "$BIN/$NAME"_TEMP.json    
    # Check if solc generated the expected JSON file
    if [ ! -f "$BIN/$NAME"_TEMP.json ]; then
        echo "Error: solc did not generate the expected output file!"
        return 1
    fi
    # Use jq to extract and format the contract details
    jq --arg contract "$DIAMOND:$NAME" '
      .contracts[$contract] | {
        abi: .abi,
        bin: .bin,
        hashes: .hashes
      }
    ' "$BIN/$NAME"_TEMP.json > "$BIN/$NAME.json"

    rm -rf "$BIN/$NAME"_TEMP.json
}

compileDiamondInitContract() {
    NAME="DiamondInit"    
    # Compile the contract and save to a temporary file
    solc --optimize --evm-version london --color --pretty-json --combined-json abi,bin,hashes $QBFT_HOME/$DIAMOND_INIT > "$BIN/$NAME"_TEMP.json    
    # Check if solc generated the expected JSON file
    if [ ! -f "$BIN/$NAME"_TEMP.json ]; then
        echo "Error: solc did not generate the expected output file!"
        return 1
    fi
    # Use jq to extract and format the contract details
    jq --arg contract "$DIAMOND_INIT:$NAME" '
      .contracts[$contract] | {
        abi: .abi,
        bin: .bin,
        hashes: .hashes
      }
    ' "$BIN/$NAME"_TEMP.json > "$BIN/$NAME.json"

    rm -rf "$BIN/$NAME"_TEMP.json
}

compileDiamondCutFacetContract() {
    NAME="DiamondCutFacet"    
    # Compile the contract and save to a temporary file
    solc --optimize --evm-version london --color --pretty-json --combined-json abi,bin,hashes $QBFT_HOME/$DIAMOND_CUT_FACET > "$BIN/$NAME"_TEMP.json    
    # Check if solc generated the expected JSON file
    if [ ! -f "$BIN/$NAME"_TEMP.json ]; then
        echo "Error: solc did not generate the expected output file!"
        return 1
    fi
    # Use jq to extract and format the contract details
    jq --arg contract "$DIAMOND_CUT_FACET:$NAME" '
      .contracts[$contract] | {
        abi: .abi,
        bin: .bin,
        hashes: .hashes
      }
    ' "$BIN/$NAME"_TEMP.json > "$BIN/$NAME.json"

    rm -rf "$BIN/$NAME"_TEMP.json
}

compileDiamondLoupeFacetContract() {
    NAME="DiamondLoupeFacet"    
    # Compile the contract and save to a temporary file
    solc --optimize --evm-version london --color --pretty-json --combined-json abi,bin,hashes $QBFT_HOME/$DIAMOND_LOUPE_FACET > "$BIN/$NAME"_TEMP.json    
    # Check if solc generated the expected JSON file
    if [ ! -f "$BIN/$NAME"_TEMP.json ]; then
        echo "Error: solc did not generate the expected output file!"
        return 1
    fi
    # Use jq to extract and format the contract details
    jq --arg contract "$DIAMOND_LOUPE_FACET:$NAME" '
      .contracts[$contract] | {
        abi: .abi,
        bin: .bin,
        hashes: .hashes
      }
    ' "$BIN/$NAME"_TEMP.json > "$BIN/$NAME.json"

    rm -rf "$BIN/$NAME"_TEMP.json
}
compileOwnershipFacetContract() {
    NAME="OwnershipFacet"    
    # Compile the contract and save to a temporary file
    solc --optimize --evm-version london --color --pretty-json --combined-json abi,bin,hashes $QBFT_HOME/$OWNERSHIP_FACET > "$BIN/$NAME"_TEMP.json    
    # Check if solc generated the expected JSON file
    if [ ! -f "$BIN/$NAME"_TEMP.json ]; then
        echo "Error: solc did not generate the expected output file!"
        return 1
    fi
    # Use jq to extract and format the contract details
    jq --arg contract "$OWNERSHIP_FACET:$NAME" '
      .contracts[$contract] | {
        abi: .abi,
        bin: .bin,
        hashes: .hashes
      }
    ' "$BIN/$NAME"_TEMP.json > "$BIN/$NAME.json"

    rm -rf "$BIN/$NAME"_TEMP.json
}

copyBinToResource(){

    if [ ! -d "$RESOURCE_PATH" ]; then
        # If it doesn't exist, create the directory
        mkdir -p "$QBFT_HOME/$RESOURCE_PATH"
        echo "Directory $RESOURCE_PATH created."
    fi
    cp -r $BIN/* $RESOURCE_PATH
}

main(){
exports
isSolcInstalled
isContractsAvailable
compileDiamondContract
compileDiamondInitContract
compileDiamondCutFacetContract
compileDiamondLoupeFacetContract
compileOwnershipFacetContract
copyBinToResource
}


main