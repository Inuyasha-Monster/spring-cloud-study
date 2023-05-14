package com.djl.cloud.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自定义服务实例选择器
 */
public class RandomServiceInstanceChoose implements ServiceInstanceChooser {

    private final DiscoveryClient discoveryClient;

    public RandomServiceInstanceChoose(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        Assert.hasText(serviceId, "serviceId not be empty");
        final List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (CollectionUtils.isEmpty(instances)) {
            throw new IllegalArgumentException("not any service instance");
        }
        return instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
    }
}
