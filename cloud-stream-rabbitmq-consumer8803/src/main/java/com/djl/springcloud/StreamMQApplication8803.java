package com.djl.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author djl
 * @create 2021/1/4 14:59
 */
@SpringBootApplication
@EnableEurekaClient
public class StreamMQApplication8803 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQApplication8803.class,args);
    }
}
