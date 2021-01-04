package com.djl.springcloud.controller;

import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import com.djl.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 * @create 2020/12/31 13:45
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return port;
    }

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("****插入结果:" + result + " hhhh");

        if (result > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort:" + port, result);
        } else {
            return new CommonResult(444, "插入数据库失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("****插入结果:" + payment);

        if (payment != null) {
            return new CommonResult(200, "查询成功,serverPort:" + port, payment);
        } else {
            return new CommonResult(444, "没有对应记录，查询ID: " + id, null);
        }
    }

    @GetMapping(value = "/payment/timeout")
    public String getTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return port;
    }

    @GetMapping(value="/payment/zipkin")
    public String paymentZipkin() {
        return "hello,i am paymentZipkin server,O(∩_∩)O哈哈~";
    }
}
