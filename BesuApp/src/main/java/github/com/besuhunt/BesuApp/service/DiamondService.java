package github.com.besuhunt.BesuApp.service;

import github.com.besuhunt.BesuApp.contracts.diamond.facets.DiamondCutFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.DiamondLoupeFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.OwnershipFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.upgrade.DiamondInit;
import github.com.besuhunt.BesuApp.utils.BesuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.util.Optional;

@Service
public class DiamondService {


    private static final Logger logger = LoggerFactory.getLogger(DiamondService.class);

    private final Web3j web3j;
    private final StaticGasProvider gasProvider;
    private final RawTransactionManager rawTransactionManager;
    private final Credentials credentials;


    public DiamondService(BesuUtil besuUtil) {
        this.web3j = besuUtil.web3j();
        this.credentials = besuUtil.credentials_Alice();
        this.gasProvider = besuUtil.staticGasProvider();
        this.rawTransactionManager = besuUtil.rawTransactionManager(this.web3j, credentials, Long.valueOf("1337"));
    }


    public void deployDiamond() throws Exception {

        logger.info("Started deploying Diamond contract...");

        logger.info("Started deploying DiamondInit contract.....");
        String diamondInitContractAddress=deployDiamondInit();
        logger.info("diamondInitContractAddress: {}",diamondInitContractAddress);

        logger.info("Started deploying DiamondCutFacet contract.....");
        String diamondCutFacetContractAddress=deployDiamondCutFacet();
        logger.info("diamondCutFacetContractAddress: {}",diamondCutFacetContractAddress);


        logger.info("Started Deploying diamondLoupeFacet contract.....");
        String diamondLoupeFacetContractAddress=deployDiamondLoupeFacet();
        logger.info("diamondLoupeFacetContractAddress: {}",diamondLoupeFacetContractAddress);


        logger.info("Started deploying OwnerShipFacet contract.....");
        String ownerShipFacetContractAddress=deployOwnershipFacet();
        logger.info("ownerShipFacetContractAddress: {}",ownerShipFacetContractAddress);

    }


    private String deployDiamondInit() throws Exception {

        DiamondInit diamondInit=DiamondInit.deploy(this.web3j,this.rawTransactionManager,gasProvider).send();

        Optional<TransactionReceipt> receipt = diamondInit.getTransactionReceipt();

        if(receipt.isEmpty()){
            logger.error("DiamondInit contract deployment receipt is empty");
            throw new Exception("DiamondInit contract deployment receipt is empty");
        }

        if(!receipt.get().isStatusOK()){
            logger.error("DiamondInit contract deployment failed: {}",receipt.get().getRevertReason());
            throw new Exception("DiamondInit contract deployment failed: "+receipt.get().getRevertReason());
        }

        return receipt.get().getContractAddress();
    }

    private String deployDiamondCutFacet() throws Exception {

        DiamondCutFacet diamondCutFacet=DiamondCutFacet.deploy(this.web3j,this.rawTransactionManager,gasProvider).send();

        Optional<TransactionReceipt> receipt=diamondCutFacet.getTransactionReceipt();

        if(receipt.isEmpty()){
            logger.error("DiamondCutFacet contract deployment receipt is empty");
            throw new Exception("DiamondCutFacet contract deployment receipt is empty");
        }

        if(!receipt.get().isStatusOK()){
            logger.error("DiamondCutFacet contract deployment failed: {}",receipt.get().getRevertReason());
            throw new Exception("DiamondCutFacet contract deployment failed: "+receipt.get().getRevertReason());
        }

        return receipt.get().getContractAddress();
    }


    private String deployDiamondLoupeFacet() throws Exception {

        DiamondLoupeFacet diamondLoupeFacet= DiamondLoupeFacet.deploy(this.web3j,this.rawTransactionManager,gasProvider).send();

        Optional<TransactionReceipt> receipt=diamondLoupeFacet.getTransactionReceipt();

        if(receipt.isEmpty()){
            logger.error("DiamondLoupeFacet contract deployment receipt is empty");
            throw new Exception("DiamondLoupeFacet contract deployment receipt is empty");
        }

        if(!receipt.get().isStatusOK()){
            logger.error("DiamondLoupeFacet contract deployment failed: {}",receipt.get().getRevertReason());
            throw new Exception("DiamondLoupeFacet contract deployment failed: "+receipt.get().getRevertReason());
        }

        return receipt.get().getContractAddress();
    }

    private String deployOwnershipFacet() throws Exception {

        OwnershipFacet ownershipFacet= OwnershipFacet.deploy(this.web3j,this.rawTransactionManager,gasProvider).send();

        Optional<TransactionReceipt> receipt=ownershipFacet.getTransactionReceipt();

        if(receipt.isEmpty()){
            logger.error("OwnershipFacet contract deployment receipt is empty");
            throw new Exception("OwnershipFacet contract deployment receipt is empty");
        }

        if(!receipt.get().isStatusOK()){
            logger.error("OwnershipFacet contract deployment failed: {}",receipt.get().getRevertReason());
            throw new Exception("OwnershipFacet contract deployment failed: "+receipt.get().getRevertReason());
        }

        return receipt.get().getContractAddress();
    }
}
