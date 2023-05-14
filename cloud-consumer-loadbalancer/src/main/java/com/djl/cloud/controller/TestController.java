package com.djl.cloud.controller;

import com.djl.cloud.config.RandomServiceInstanceChoose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Resource(name = "restTemplateForLoadBalancer")
    private RestTemplate restTemplateForLoadBalancer;

    @GetMapping("/test")
    public String test() {
        final RandomServiceInstanceChoose choose = new RandomServiceInstanceChoose(discoveryClient);
        final ServiceInstance serviceInstance = choose.choose("nacos-config-client");
        final String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/getCurrentPort";
        return restTemplate.getForEntity(url, String.class).getBody();
    }

    @GetMapping("/testForLoadBalancer")
    public String testForLoadBalancer() {
        final String url = "http://nacos-config-client/getCurrentPort";
        return restTemplateForLoadBalancer.getForEntity(url, String.class).getBody();
    }
}
