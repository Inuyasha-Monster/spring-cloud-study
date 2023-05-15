package com.djl.study.controller;

import com.djl.study.BootConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 */
@RestController
@RefreshScope
public class ConfigController implements ApplicationContextAware, ApplicationListener<EnvironmentChangeEvent> {

    private ApplicationContext applicationContext;

    @Value("${book.author:xxxxx}")
    private String bookAuthor;

    @Autowired
    private BootConfig bootConfig;

    @GetMapping("/config")
    public String config() {
        StringBuilder sb = new StringBuilder();
        sb.append("env.get('book,category')=" + applicationContext.getEnvironment().getProperty("book.category", "unknown"))
                .append("<br/>env.get('book.author')=" + applicationContext.getEnvironment().getProperty("book.author", "unknown"))
                .append("<br/>bookAuthor = " + bookAuthor)
                .append("<br/>bootConfig = " + bootConfig);
        return sb.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        System.out.println("event = " + event);
    }
}
