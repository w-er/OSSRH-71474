package com.wencoder.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/")
    public Demo get(){
        return demoService.get();
    }
}
