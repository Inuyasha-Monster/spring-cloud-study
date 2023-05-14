package com.djl.cloud.config;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyRibbonRuleConfiguration {
    @Bean
    public IRule garyRule() {
        return new GaryRule();
    }

    public static class GaryRule extends AbstractLoadBalancerRule {

        @Override
        public void initWithNiwsConfig(IClientConfig clientConfig) {

        }

        @Override
        public Server choose(Object key) {
            boolean grayInvocation = false;
            try {
                String grayTag = RibbonRequestContextHolder.get().get("Gray");
                if (!StringUtils.isEmpty(grayTag) && grayTag.equals(Boolean.TRUE.toString())) {
                    grayInvocation = true;
                }

                List<Server> serverList = this.getLoadBalancer().getReachableServers();
                List<Server> grayServerList = new ArrayList<>();
                List<Server> normalServerList = new ArrayList<>();
                for (Server server : serverList) {
                    NacosServer nacosServer = (NacosServer) server;
                    if (nacosServer.getMetadata().containsKey("gray") && nacosServer.getMetadata().get("gray").equals("true")) {
                        grayServerList.add(server);
                    } else {
                        normalServerList.add(server);
                    }
                }

                if (grayInvocation) {
                    return grayServerList.get(ThreadLocalRandom.current().nextInt(grayServerList.size()));
                } else {
                    return normalServerList.get(ThreadLocalRandom.current().nextInt(normalServerList.size()));
                }
            } finally {
                RibbonRequestContextHolder.clear();
            }
        }
    }
}
