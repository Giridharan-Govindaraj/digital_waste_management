# besu_hunt

to generate Wrapper for a contract we need .abi and .bin files which is placed in he resource folder

the following command used for wrapper generation

solidity generate -b src/main/resources/contracts/Storage/Storage.bin -a src/main/resources/contracts/Storage/Storage.abi -o src/main/java -p github.com.besuhunt.contracts