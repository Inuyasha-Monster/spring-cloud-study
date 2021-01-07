package com.djl.springcloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author djl
 * @create 2021/1/7 15:42
 */
@Service
@Slf4j
public class OrderService {

    @SentinelResource("getOrder")
    public String testGetOrder() {
        log.info("----------testGetOrder");
        return "order";
    }
}
