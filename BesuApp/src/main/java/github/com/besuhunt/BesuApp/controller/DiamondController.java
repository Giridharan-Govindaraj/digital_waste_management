package github.com.besuhunt.BesuApp.controller;

import github.com.besuhunt.BesuApp.service.DiamondService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("diamond/v1/")
public class DiamondController {

    private final DiamondService diamondService;

    public DiamondController(DiamondService diamondService) {
        this.diamondService = diamondService;
    }


    @PostMapping("deploy")
    public String deploy() throws Exception {
        return diamondService.deployDiamond();
    }
}
