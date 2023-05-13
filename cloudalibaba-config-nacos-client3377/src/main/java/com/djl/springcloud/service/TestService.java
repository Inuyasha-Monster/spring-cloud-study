package com.djl.springcloud.service;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
//@NacosInjected
public class TestService {
    //    @Value("${config.info}")
    // 无效
    @NacosValue(value = "${config.info}", autoRefreshed = true)
    private String configInfo;
}
