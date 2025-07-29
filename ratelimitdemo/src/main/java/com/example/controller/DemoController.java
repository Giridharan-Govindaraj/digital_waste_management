package com.example.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DemoController {

    @GetMapping
    public String getResult() throws InterruptedException {
        Thread.sleep(500);
        return "success";
    }

}
