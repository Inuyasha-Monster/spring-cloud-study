package com.djl.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author djl
 * @create 2020/12/31 14:20
 */
@Configuration
public class ApplicationContextConfig {

//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

//    @Bean(name = "RestTemplate2")
//    public RestTemplate getRestTemplate2() {
//        return new RestTemplate();
//    }
}
