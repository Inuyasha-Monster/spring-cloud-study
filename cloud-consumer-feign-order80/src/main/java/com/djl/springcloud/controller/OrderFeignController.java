package com.djl.springcloud.controller;

import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import com.djl.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author djl
 * @create 2021/1/2 12:25
 */
@RestController
public class OrderFeignController {

    @Resource
    PaymentService paymentService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable(name = "id") long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/timeout")
    public String getTimeout() {
        return paymentService.getTimeout();
    }
}
