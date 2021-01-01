package com.djl.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author djl
 * @create 2021/1/1 10:58
 */
@RestController
public class ConsulController {
    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/payment/consul")
    public String paymentConsul() {
        return "springcloud with consul: " + port + "\t" + UUID.randomUUID().toString();
    }
}
