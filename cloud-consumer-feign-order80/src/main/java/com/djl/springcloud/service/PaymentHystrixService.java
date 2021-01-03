package com.djl.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author djl
 * @create 2021/1/3 9:40
 * cloud-provider-hystrix-payment
 */
@Component
@FeignClient(name = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = PaymentHystrixServiceFallbackImpl.class)
public interface PaymentHystrixService {

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_Timeout(@PathVariable(name = "id") int id);

    @GetMapping("/payment/hystrix/exception")
    String payment_Exception();
}
