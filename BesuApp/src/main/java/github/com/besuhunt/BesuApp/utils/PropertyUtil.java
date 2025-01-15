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


    @Value("${besu.contract.diamond.DiamondCutFacet.json}")
    private Resource diamondCutFacetJSON;

    @Value("${besu.contract.diamond.DiamondLoupeFacet.json}")
    private Resource diamondLoupeFacetJSON;

    @Value("${besu.contract.diamond.OwnershipFacet.json}")
    private Resource ownershipFacetJSON;
    @Value("${besu.contract.diamond.DiamondInit.json}")
    private Resource diamondInitJSON;

    public Resource getDiamondCutFacetJSON() {
        return diamondCutFacetJSON;
    }

    public Resource getDiamondLoupeFacetJSON() {
        return diamondLoupeFacetJSON;
    }

    public Resource getOwnershipFacetJSON() {
        return ownershipFacetJSON;
    }

    public Resource getDiamondInitJSON() {
        return diamondInitJSON;
    }

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
