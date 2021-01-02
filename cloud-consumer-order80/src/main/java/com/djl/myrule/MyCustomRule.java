package com.djl.myrule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author djl
 * @create 2021/1/2 11:53
 */
public class MyCustomRule implements IRule {

    private ILoadBalancer iLoadBalancer;

    /**
     * 自定义实现选取服务规则:选择第一个可达的服务作为有效匹配
     *
     * @param o
     * @return
     */
    @Override
    public Server choose(Object o) {
        List<Server> reachableServers = iLoadBalancer.getReachableServers();
        if (reachableServers.size() <= 0) {
            return null;
        }
        return reachableServers.stream().findFirst().get();
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {

        this.iLoadBalancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.iLoadBalancer;
    }
}
