package com.djl.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(proxyBeanMethods = false)
@EnableDiscoveryClient(autoRegister = false)
public class LoadBalancerDemo {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateForLoadBalancer() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerDemo.class, args);
    }
}
