package github.com.besuhunt.BesuApp.controller;

import github.com.besuhunt.BesuApp.service.DiamondService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("getFacets")
    public void getFacets(@RequestParam String address, @RequestParam String function) throws Exception {
        diamondService.getFacets(address,function);
    }
}
