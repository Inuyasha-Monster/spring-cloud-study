package com.djl.springcloud.controller;

import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @author djl
 * @create 2020/12/31 14:21
 */
@RestController
@Slf4j
public class OrderController {

//    private static final String PAYMENT_URL="http://localhost:8001";

    //通过在eureka上注册过的微服务名称调用
    private static final String PAYMENT_URL = "http://CLOUD-PROVIDER-SERVICE";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Resource(name = "RestTemplate")
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }


    @GetMapping("/consumer/discovery")
    public Object getDiscovery() {
        final List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println("service = " + service);
        }
        final List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-SERVICE");
        for (ServiceInstance instance : instances) {
            final String host = instance.getHost();
            final URI uri = instance.getUri();
            final int port = instance.getPort();
            final Map<String, String> metadata = instance.getMetadata();
            System.out.println(host + " " + " " + uri + " " + port + " " + metadata);
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") long id) {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }
        return new CommonResult<>(444, "操作失败");
    }

    @Resource(name = "RestTemplate2")
    private RestTemplate restTemplate2;

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB() {
        String result = restTemplate2.getForObject(PAYMENT_URL + "/payment/lb", String.class);
        return result;
    }
}
