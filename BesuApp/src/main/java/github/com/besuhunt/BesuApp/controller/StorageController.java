package github.com.besuhunt.BesuApp.controller;


import github.com.besuhunt.BesuApp.service.StorageService;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@RestController
@RequestMapping("storage/v1")
public class StorageController {


    private final StorageService storageService;


    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/deploy")
    public String deploy() throws Exception {
       return this.storageService.deploy_();
    }
    @GetMapping("/owner")
    public String getOwner( @RequestParam String contractAddress) throws Exception {
        return this.storageService.getOwner(contractAddress);
    }

    @GetMapping("/number")
    public BigInteger getNumber(@RequestParam String contractAddress) throws Exception {
        return this.storageService.getNumber(contractAddress);
    }
    @PostMapping("/number")
    public TransactionReceipt setNumber(@RequestParam String contractAddress, @RequestParam BigInteger number) throws Exception {
        return this.storageService.setNumber(contractAddress,number);
    }
}
