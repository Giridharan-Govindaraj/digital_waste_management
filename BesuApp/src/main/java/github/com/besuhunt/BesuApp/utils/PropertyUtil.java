package github.com.besuhunt.BesuApp.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
}
