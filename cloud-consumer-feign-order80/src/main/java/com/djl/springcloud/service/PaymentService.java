package com.djl.springcloud.service;

import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author djl
 * @create 2021/1/2 12:22
 */
@Component
@FeignClient(name = "CLOUD-PROVIDER-SERVICE")
public interface PaymentService {
    @GetMapping(value = "/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable(name = "id") long id);

    @GetMapping(value = "/payment/timeout")
    String getTimeout();
}
