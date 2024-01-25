package com.login.logintest.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "onyx")
@AllArgsConstructor
public class DemoController {

    @GetMapping(path = "hello")
    public String hello(){
        return "hello";
    }
}
