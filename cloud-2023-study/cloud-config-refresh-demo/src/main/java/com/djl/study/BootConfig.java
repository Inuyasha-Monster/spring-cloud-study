package com.djl.study;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * sb玩意儿需要get set方法才能填充配置数据
 * @author djl
 */
@ConfigurationProperties(prefix = "book")
@Component
public class BootConfig {
    private String author;
    private String category;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BootConfig{" +
                "author='" + author + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
