package com.djl.springcloud.controller;

import com.djl.springcloud.service.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2021/1/7 15:56
 */
@RestController
public class AController {

    @Autowired
    private AService aService;

    @GetMapping(value = "/testA1")
    public String testA() {
        return aService.testA();
    }

    @GetMapping(value = "/testB1")
    public String testB() {
        return aService.testA();
    }
}
