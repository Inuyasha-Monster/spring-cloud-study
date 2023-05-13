package com.djl.springcloud.controller;

import com.djl.springcloud.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2021/1/5 21:14
 */
@RestController
@RefreshScope // 支持Nacos的动态刷新功能
public class ConfigClientController {

    @Value("${config.info}")
//    @NacosValue(value = "${config.info}", autoRefreshed = true)
    private String configInfo;

    @Autowired
    private TestService testService;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}