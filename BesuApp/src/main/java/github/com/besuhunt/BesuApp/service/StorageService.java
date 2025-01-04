package github.com.besuhunt.BesuApp.service;

import github.com.besuhunt.BesuApp.contracts.Storage;
import github.com.besuhunt.BesuApp.utils.BesuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

@Service
public class StorageService {


    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


    private final Web3j web3j;
    private final ContractGasProvider gasProvider;
    private final RawTransactionManager rawTransactionManager;

    public StorageService(BesuUtil besuUtil) {
        this.web3j = besuUtil.web3j();
        Credentials credentials = besuUtil.credentials_Alice();
        this.gasProvider = besuUtil.staticGasProvider();
        this.rawTransactionManager = besuUtil.rawTransactionManager(this.web3j, credentials);
    }

    public String deploy() throws Exception {
        logger.info("deploying storage contract");
        Storage storage = Storage.deploy(
                web3j,
                rawTransactionManager,
                gasProvider
        ).send();

        String address=storage.getContractAddress();
        logger.info("address:{}",address);
        logger.info("contract deployed successfully");
        return address;
    }

    public String getOwner(String contractAddress) throws Exception {
        logger.info("getting the owner from contract {}", contractAddress);
        Storage storage = Storage.load(
                contractAddress,
                web3j,
                rawTransactionManager,
                gasProvider
        );
        String owner=storage.getOwner().send();
        logger.info("owner:{}",owner);
        logger.info("get owner transaction executed successfully");
        return owner;
    }

    public BigInteger getNumber(String contractAddress) throws Exception {
        logger.info("getting the number from contract {}",contractAddress);
        Storage storage = Storage.load(
                contractAddress,
                web3j,
                rawTransactionManager,
                gasProvider
        );
        BigInteger number=storage.getNumber().send();
        logger.info("number:{}",number);
        logger.info("get number transaction executed successfully");
        return number;
    }


    public TransactionReceipt setNumber (String contractAddress, BigInteger number) throws Exception {
        logger.info("setting the number {} to storage contract {}",number,contractAddress);
        Storage storage = Storage.load(
                contractAddress,
                web3j,
                rawTransactionManager,
                gasProvider
        );
        TransactionReceipt receipt = storage.setNumber(number).send();
        logger.info("receipt {}",receipt);
        if(!receipt.isStatusOK()){
            logger.error("Transaction failed");
            throw new Exception("Transaction failed");
        }
        logger.info("set number transaction executed successfully");
        return receipt;
    }
}
