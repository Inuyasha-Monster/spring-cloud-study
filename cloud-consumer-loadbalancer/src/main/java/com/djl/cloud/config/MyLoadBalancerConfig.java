package com.djl.cloud.config;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(value = "nacos-config-client", configuration = {
        MyRibbonRuleConfiguration.class
})
@LoadBalancerClient(value = "nacos-config-client", configuration = {
        MyReactorLoadBalancerConfig.class
})
public class MyLoadBalancerConfig {

}
