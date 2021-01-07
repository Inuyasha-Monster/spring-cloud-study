package com.djl.springcloud.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * @author djl
 * @create 2021/1/7 15:57
 */
@Service
public class AService {

    //  指定资源名，并指定降级方法
    @SentinelResource(value = "testA", blockHandler = "testAFallback")
    public String testA() {
        return "testA";
    }

    public String testAFallback(BlockException ex) {
        return "ex testA";
    }
}
