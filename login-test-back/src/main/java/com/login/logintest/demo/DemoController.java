package com.login.logintest.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "onyx")
@AllArgsConstructor
public class DemoController {

    @GetMapping(path = "hello")
    public String hello(){
        return "hello";
    }

    @GetMapping(path = "good")
    public ResponseEntity<List<String>> good(){
        return ResponseEntity.ok(Arrays.asList("hello", "it", "works"));
    }
}
