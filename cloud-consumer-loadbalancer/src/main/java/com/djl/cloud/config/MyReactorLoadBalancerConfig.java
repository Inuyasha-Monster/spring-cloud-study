package com.djl.cloud.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.reactive.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.reactive.Request;
import org.springframework.cloud.client.loadbalancer.reactive.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MyReactorLoadBalancerConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> myReactiveLoadBalancer(
            Environment environment,
            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(loadBalancerClientFactory.getLazyProvider(name,
                ServiceInstanceListSupplier.class), name);
    }

    private static class RandomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
        private final ObjectProvider<ServiceInstanceListSupplier> lazyProvider;
        private final String name;

        public RandomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> lazyProvider, String name) {
            this.lazyProvider = lazyProvider;
            this.name = name;
        }

        @Override
        public Mono<Response<ServiceInstance>> choose(Request request) {
            final ServiceInstanceListSupplier supplier = lazyProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
            return supplier.get().next().map(this::getInstanceResponse);
        }

        private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> serviceInstances) {
            if (CollectionUtils.isEmpty(serviceInstances)) {
                return new EmptyResponse();
            }
            return new DefaultResponse(serviceInstances.get(ThreadLocalRandom.current().nextInt(serviceInstances.size())));
        }
    }
}
