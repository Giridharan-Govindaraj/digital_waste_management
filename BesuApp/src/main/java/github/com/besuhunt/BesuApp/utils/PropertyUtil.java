package github.com.besuhunt.BesuApp.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class PropertyUtil {


    @Value("${besu.privatekey.alice}")
    private String pvk_alice;

    @Value("${besu.privatekey.bob}")
    private String pvk_bob;

    @Value("${besu.privatekey.john}")
    private String pvk_john;

    @Value("${besu.rpc}")
    private String rpc;

    @Value("${besu.gaslimit}")
    private Long gasLimit;

    @Value("${besu.chainID}")
    private Long chainID;


    @Value("${besu.contract.diamond.facet.DiamondCutFacet.abi}")
    private Resource diamondCutFacetABI;

    @Value("${besu.contract.diamond.facet.DiamondLoupeFacet.abi}")
    private Resource diamondLoupeFacetABI;

    @Value("${besu.contract.diamond.facet.OwnershipFacet.abi}")
    private Resource ownershipFacetABI;


    public Resource getDiamondCutFacetABI(){ return diamondCutFacetABI;}

    public Resource getDiamondLoupeFacetABI(){ return diamondLoupeFacetABI;}

    public Resource getOwnershipFacetABI(){ return ownershipFacetABI;}

    public String getPvk_alice() {
        return pvk_alice;
    }

    public String getPvk_bob() {
        return pvk_bob;
    }

    public String getPvk_john() {
        return pvk_john;
    }

    public String getRpc() {
        return rpc;
    }

    public Long getGasLimit(){
        return gasLimit;
    }

    public Long getChainID(){ return chainID;}
}
