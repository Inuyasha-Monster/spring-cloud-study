package com.djl.springcloud.confg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MybatisConfig
 * @description:
 * @author: djl
 * @create: 2020-06-12 09:19
 */

@Configuration
@MapperScan("com.djl.springcloud.dao")
public class MybatisConfig {
}
