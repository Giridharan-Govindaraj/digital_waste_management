package github.com.besuhunt.BesuApp.utils;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Component
public class BesuUtil {

    private final PropertyUtil propertyUtil;

    public BesuUtil(PropertyUtil propertyUtil) {
        this.propertyUtil = propertyUtil;
    }


    public  Web3j web3j(){
        return Web3j.build(new HttpService(this.propertyUtil.getRpc()));
    }

    public  Credentials credentials_Alice(){
        return Credentials.create(this.propertyUtil.getPvk_alice());
    }

    public  Credentials credentials_Bob(){
        return Credentials.create(this.propertyUtil.getPvk_bob());
    }

    public  Credentials credentials_John(){
        return Credentials.create(this.propertyUtil.getPvk_john());
    }


    public  RawTransactionManager rawTransactionManager(Web3j web3j, Credentials credentials){
        return new RawTransactionManager(web3j,credentials,propertyUtil.getChainID());
    }

    public  StaticGasProvider staticGasProvider(){
        return new StaticGasProvider(
                BigInteger.ZERO,
                BigInteger.valueOf(this.propertyUtil.getGasLimit())
        );
    }

    public  DefaultGasProvider defaultGasProvider(){
        return new DefaultGasProvider();
    }



}
