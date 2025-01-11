package github.com.besuhunt.BesuApp.service;

import github.com.besuhunt.BesuApp.contracts.Storage;
import github.com.besuhunt.BesuApp.utils.BesuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class StorageService {


    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);


    private final Web3j web3j;
    private final StaticGasProvider gasProvider;
    private final RawTransactionManager rawTransactionManager;
    private final Credentials credentials;
    public StorageService(BesuUtil besuUtil) {
        this.web3j = besuUtil.web3j();
        this.credentials = besuUtil.credentials_Alice();
        this.gasProvider = besuUtil.staticGasProvider();
        this.rawTransactionManager = besuUtil.rawTransactionManager(this.web3j, credentials, Long.valueOf("1337"));
    }

    public String deploy() throws Exception {
        logger.info("deploying storage contract");
        logger.info("gas price used:"+gasProvider.getGasPrice());
        logger.info("gas limit used:"+gasProvider.getGasLimit());

        logger.info("gas price used for func:"+gasProvider.getGasPrice("deploy"));

        Storage storage = Storage.deploy(
                web3j,
                credentials,
                gasProvider
        ).send();

        Optional<TransactionReceipt> receipt = storage.getTransactionReceipt();

        if(receipt.isPresent() && receipt.get().isStatusOK()){
            logger.info("receipt:{}",receipt.toString());
        }else{
            logger.error("receipt:{}",receipt.toString());
        }

        String address=storage.getContractAddress();
        logger.info("address:{}",address);
        logger.info("contract deployed successfully");
        return address;
    }


    public String deploy_() throws Exception {

        BigInteger nonce = this.web3j.ethGetTransactionCount(this.credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get().getTransactionCount();
        logger.info("nonce: {}",nonce);
        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                nonce,
                gasProvider.getGasPrice(),
                gasProvider.getGasLimit(),
                BigInteger.ZERO,
                Storage.BINARY
        );

        logger.info("rawTransaction: {}",rawTransaction);
        byte[] signedMessage=TransactionEncoder.signMessage(rawTransaction,1337,this.credentials);
        logger.info("signedMessage: {}",signedMessage);
        String message=Numeric.toHexString(signedMessage);
        logger.info("message: {}",message);
        EthSendTransaction transaction = this.web3j.ethSendRawTransaction(message).send();
        logger.info("transaction: {}",transaction);
        if(transaction.hasError()){
            logger.error("Error Code:",transaction.getError().getCode());
            logger.error("Error message:",transaction.getError().getMessage());
        }

        Optional<TransactionReceipt> receipt=getTransactionReceipt(transaction.getTransactionHash());
        for(int i=0;i<40;i++){
            if(!receipt.isPresent()){
                Thread.sleep(1500);
                receipt=getTransactionReceipt(transaction.getTransactionHash());
            }else{
                break;
            }
        }
        logger.info("contractaddress: {}",receipt.get().getContractAddress());
        logger.info("status: {}",receipt.get().getStatus());

        return receipt.get().getContractAddress();
    }


    private Optional<TransactionReceipt> getTransactionReceipt(String hash) throws ExecutionException, InterruptedException {
        return this.web3j.ethGetTransactionReceipt(hash).sendAsync().get().getTransactionReceipt();
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
                credentials,
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
