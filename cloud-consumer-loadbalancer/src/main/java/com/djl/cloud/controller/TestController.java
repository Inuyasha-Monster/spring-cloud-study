package com.djl.cloud.controller;

import com.djl.cloud.config.RandomServiceInstanceChoose;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Resource(name = "restTemplateForLoadBalancer")
    private RestTemplate restTemplateForLoadBalancer;

    @Resource(name = "grayRestTemplate")
    private RestTemplate grayRestTemplate;

    @Resource(name = "dynamicGrayRestTemplate")
    private RestTemplate dynamicGrayRestTemplate;

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

    @GetMapping("/testForGrayRestTemplate")
    public String testForGrayRestTemplate(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {
            headers.set("Gray", request.getHeader("Gray").equals("true") ? "true" : "false");
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);
        final String url = "http://nacos-config-client/getCurrentPort";
        return grayRestTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/testForDynamicGrayRestTemplate")
    public String testForDynamicGrayRestTemplate(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtils.isNotEmpty(request.getHeader("Gray"))) {
            headers.set("Gray", request.getHeader("Gray"));
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);
        final String url = "http://nacos-config-client/getCurrentPort";
        return dynamicGrayRestTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }
}
