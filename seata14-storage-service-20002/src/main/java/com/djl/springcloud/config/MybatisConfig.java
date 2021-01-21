package com.djl.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MybatisConfig
 * @description:
 * @author: liusCoding
 * @create: 2020-06-12 10:19
 */

@Configuration
@MapperScan({"com.djl.springcloud.dao"})
public class MybatisConfig {
}
