package com.wencoder.demo.fill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/{id}")
    public UserVO getUser(@PathVariable String id) {
        return testService.getUser(id);
    }
}
