package com.djl.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author djl
 * @create 2021/1/1 11:31
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        // 自定义返回随机策略
//        return new RandomRule();
        return new MyCustomRule();
    }
}
