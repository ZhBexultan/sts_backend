package com.spring.sts.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestControllerV1 {

    @GetMapping("/")
    public String index() {
        return "Hello SoulToSoul";
    }

}
