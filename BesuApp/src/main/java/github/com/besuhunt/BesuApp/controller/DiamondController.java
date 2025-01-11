package github.com.besuhunt.BesuApp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("diamond/v1/")
public class DiamondController {


    @PostMapping("deploy")
    public void deploy(){

    }
}
