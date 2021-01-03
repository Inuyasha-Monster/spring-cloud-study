package com.djl.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.rmi.activation.ActivationGroup_Stub;

/**
 * @author djl
 * @create 2021/1/3 12:28
 */
@SpringBootApplication
@EnableEurekaClient
public class GateWayApplication9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication9527.class, args);
    }
}
