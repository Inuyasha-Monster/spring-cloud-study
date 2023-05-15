package com.djl.cloud;

import com.djl.cloud.config.RibbonRequestContextHolder;
import com.djl.cloud.rest.GrayInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(proxyBeanMethods = false)
@EnableDiscoveryClient(autoRegister = false)
public class LoadBalancerDemo {

    private static final String GARY = "Gray";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplateForLoadBalancer() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public RestTemplate grayRestTemplate() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            if (request.getHeaders().containsKey(GARY)) {
                final String gray = request.getHeaders().getFirst(GARY);
                if (StringUtils.hasText(gray) && Boolean.TRUE.toString().equalsIgnoreCase(gray)) {
                    RibbonRequestContextHolder.get().put(GARY, "true");
                }
            }
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    @Bean
    @LoadBalanced
    public RestTemplate dynamicGrayRestTemplate(GrayInterceptor grayInterceptor) {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(grayInterceptor);
        return restTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerDemo.class, args);
    }
}
