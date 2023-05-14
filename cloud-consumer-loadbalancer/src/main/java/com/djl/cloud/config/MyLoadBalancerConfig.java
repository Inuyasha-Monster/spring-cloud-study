package com.djl.cloud.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClient(value = "nacos-config-client", configuration = {
        MyReactorLoadBalancerConfig.class
})
public class MyLoadBalancerConfig {

}
