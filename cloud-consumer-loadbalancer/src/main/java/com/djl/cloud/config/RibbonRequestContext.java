package com.djl.cloud.config;

import java.util.HashMap;
import java.util.Map;

public class RibbonRequestContext {
    private final Map<String, String> attr = new HashMap<>();

    public String get(String key) {
        return attr.get(key);
    }

    public void put(String k, String v) {
        attr.put(k, v);
    }

    public void remove(String key) {
        attr.remove(key);
    }
}
