package com.djl.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @className: SeataOrderApplication2001
 * @description:
 * @author: djlnet
 * @create: 2020-06-12 09:13
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
public class SeataOrderApplication20001 {
    public static void main(String[] args) {
        SpringApplication.run(SeataOrderApplication20001.class, args);
    }
}
