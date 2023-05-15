package com.djl.cloud.rest;

import com.djl.cloud.config.RibbonRequestContextHolder;
import com.djl.cloud.config.TrafficRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrayInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private TrafficRuleConfiguration.TrafficRule rule;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (rule.getType().equalsIgnoreCase("header")) {
            if (request.getHeaders().containsKey(rule.getName())) {
                String value = request.getHeaders().get(rule.getName()).iterator().next();
                if (value.equals(rule.getValue())) {
                    RibbonRequestContextHolder.get().put("Gray", Boolean.TRUE.toString());
                }
            }
        } else if (rule.getType().equalsIgnoreCase("param")) {
            String query = request.getURI().getQuery();
            String[] queryKV = query.split("&");
            for (String queryItem : queryKV) {
                String[] queryInfo = queryItem.split("=");
                if (queryInfo[0].equalsIgnoreCase(rule.getName()) && queryInfo[1].equals(rule.getValue())) {
                    RibbonRequestContextHolder.get().put("Gray", Boolean.TRUE.toString());
                }
            }
        }
        return execution.execute(request, body);
    }
}
