package com.djl.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2021/1/3 16:17
 * 将远程配置读取返回到浏览器
 */
@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${data.user.username}")
    private String username;

    @GetMapping("/userInfo")
    public String getUserInfo() {
        return username;
    }
}
