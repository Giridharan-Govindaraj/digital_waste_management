package github.com.besuhunt.BesuApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.besuhunt.BesuApp.constants.FacetCutAction;
import github.com.besuhunt.BesuApp.contracts.diamond.Diamond;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.DiamondCutFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.DiamondLoupeFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.facets.OwnershipFacet;
import github.com.besuhunt.BesuApp.contracts.diamond.upgrade.DiamondInit;
import github.com.besuhunt.BesuApp.utils.BesuUtil;
import github.com.besuhunt.BesuApp.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DiamondService {


    private static final String FUNCTION="function";
    private static final String TYPE="type";
    private static final String INPUTS="inputs";
    private static final String NAME="name";

    private static final Logger logger = LoggerFactory.getLogger(DiamondService.class);

    private final Web3j web3j;
    private final StaticGasProvider gasProvider;
    private final RawTransactionManager rawTransactionManager;
    private final Credentials credentials;
    private final PropertyUtil propertyUtil;


    public DiamondService(BesuUtil besuUtil, PropertyUtil propertyUtil) {
        this.web3j = besuUtil.web3j();
        this.credentials = besuUtil.credentials_Alice();
        this.gasProvider = besuUtil.staticGasProvider();
        this.rawTransactionManager = besuUtil.rawTransactionManager(this.web3j, credentials);
        this.propertyUtil = propertyUtil;
    }


    public String deployDiamond() throws Exception {


        List<Diamond.FacetCut> facetCuts=new ArrayList<Diamond.FacetCut>();

        logger.info("Started deploying Diamond contract...");

        logger.info("Started deploying DiamondInit contract.....");
        String diamondInitContractAddress=deployDiamondInit();
        logger.info("diamondInitContractAddress: {}",diamondInitContractAddress);

        logger.info("Started deploying DiamondCutFacet contract.....");
        String diamondCutFacetContractAddress=deployDiamondCutFacet();
        logger.info("diamondCutFacetContractAddress: {}",diamondCutFacetContractAddress);

        logger.info("Started generating function signatures of DiamondCutFacet contract.....");
        DynamicArray<Bytes4> diamondCutFacetSelectors = getSelectors(this.propertyUtil.getDiamondCutFacetABI().getFile());

        Diamond.FacetCut diamondCutFacetCut = getFacetCut(diamondCutFacetContractAddress, FacetCutAction.ADD, diamondCutFacetSelectors);
        facetCuts.add(diamondCutFacetCut);

        logger.info("Started Deploying diamondLoupeFacet contract.....");
        String diamondLoupeFacetContractAddress=deployDiamondLoupeFacet();
        logger.info("diamondLoupeFacetContractAddress: {}",diamondLoupeFacetContractAddress);

        logger.info("Started generating function signatures of diamondLoupeFacet contract.....");
        DynamicArray<Bytes4> diamondLoupeFacetSelectors = getSelectors(this.propertyUtil.getDiamondLoupeFacetABI().getFile());

        Diamond.FacetCut diamondLoupeFacetCut = getFacetCut(diamondLoupeFacetContractAddress, FacetCutAction.ADD, diamondLoupeFacetSelectors);
        facetCuts.add(diamondLoupeFacetCut);

        logger.info("Started deploying OwnerShipFacet contract.....");
        String ownerShipFacetContractAddress=deployOwnershipFacet();
        logger.info("ownerShipFacetContractAddress: {}",ownerShipFacetContractAddress);

        logger.info("Started generating function signatures of OwnerShipFacet contract.....");
        DynamicArray<Bytes4>  ownerShipFacetSelectors = getSelectors(this.propertyUtil.getOwnershipFacetABI().getFile());

        Diamond.FacetCut ownerShipFacetFacetCut = getFacetCut(ownerShipFacetContractAddress, FacetCutAction.ADD, ownerShipFacetSelectors);
        facetCuts.add(ownerShipFacetFacetCut);

        logger.info("Creating function for init");
        Function function = new Function(
                "init", // Function name
                Collections.emptyList(), // No input parameters
                Collections.emptyList()  // No output parameters
        );

        // Encode the function call to send it as a transaction
        String encodedFunction = FunctionEncoder.encode(function);

        byte[] byteArray= Numeric.hexStringToByteArray(encodedFunction);
        if (byteArray.length != 4) {
            throw new IllegalArgumentException("Hex string must represent exactly 4 bytes");
        }

        // Create Bytes4 object and add to list
        Bytes4 bytes4Selector = new Bytes4(byteArray);

        logger.info("encodedFunction:{}",encodedFunction);
        Diamond.DiamondArgs diamondArgs = getDiamondArgs(credentials.getAddress(), diamondInitContractAddress, bytes4Selector.getValue());

        logger.info("Started deploying Diamond contract.....");
        String diamondContractAddress=deployDiamondContract(facetCuts,diamondArgs);
        logger.info("diamondContractAddress: {}",diamondContractAddress);

        return diamondContractAddress;

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


    private String deployDiamondContract(List<Diamond.FacetCut> _diamondCut, Diamond.DiamondArgs _args) throws Exception {

        Diamond diamond = Diamond.deploy(
                this.web3j,
                this.rawTransactionManager,
                this.gasProvider,
                BigInteger.ZERO,
                _diamondCut,
                _args
        ).send();


        Optional<TransactionReceipt> receipt=diamond.getTransactionReceipt();

        if(receipt.isEmpty()){
            logger.error("Diamond contract deployment receipt is empty");
            throw new Exception("Diamond contract deployment receipt is empty");
        }

        if(!receipt.get().isStatusOK()){
            logger.error("Diamond contract deployment failed: {}",receipt.get().getRevertReason());
            throw new Exception("Diamond contract deployment failed: "+receipt.get().getRevertReason());
        }

        return receipt.get().getContractAddress();
    }






    private DynamicArray<Bytes4> getSelectors(File file) throws IOException {
        ObjectMapper obj=new ObjectMapper();
        JsonNode abiJson=obj.readTree(file);
        List<Bytes4> functionSignatures = new ArrayList<>();
        for (JsonNode entry : abiJson) {
            if (FUNCTION.equals(entry.get(TYPE).asText())) {
                String name = entry.get(NAME).asText();
                StringBuilder signatureBuilder = new StringBuilder(name).append("(");
                JsonNode inputs = entry.get(INPUTS);
                for (int i = 0; i < inputs.size(); i++) {
                    if (i > 0) signatureBuilder.append(",");
                    signatureBuilder.append(inputs.get(i).get(TYPE).asText());
                }
                signatureBuilder.append(")");

                String hexString=Hash.sha3(signatureBuilder.toString()).substring(0,10);
                byte[] byteArray= Numeric.hexStringToByteArray(hexString);
                if (byteArray.length != 4) {
                    throw new IllegalArgumentException("Hex string must represent exactly 4 bytes");
                }
                // Create Bytes4 object and add to list
                Bytes4 bytes4Selector = new Bytes4(byteArray);

                functionSignatures.add(bytes4Selector);
            }
        }

        return new DynamicArray<>(functionSignatures);
    }

    private Diamond.FacetCut getFacetCut(String facetAddress, FacetCutAction action,DynamicArray<Bytes4> selector){

       return new Diamond.FacetCut(new Address(facetAddress), new Uint8(action.ordinal()),selector);
    }

    private Diamond.DiamondArgs getDiamondArgs(String owner, String address, byte[] functionCall){
        return new Diamond.DiamondArgs(owner,address,functionCall);
    }


}
