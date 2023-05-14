package com.djl.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/getCurrentPort")
    public String getCurrentPort() {
        return String.valueOf(serverProperties.getPort());
    }
}
