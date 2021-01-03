package com.djl.springcloud.controller;

import com.djl.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author djl
 * @create 2021/1/3 9:40
 */
@RestController
//@DefaultProperties(defaultFallback = "paymentInfo_GlobalFallbackHandler")
public class OrderHystrixController {

    @Resource
    PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//            })
//    @HystrixCommand
    public String paymentInfo_Timeout(@PathVariable(name = "id") int id) {
//        int i = 10 / 0;
        return paymentHystrixService.paymentInfo_Timeout(id);
    }

    private String paymentInfo_TimeoutHandler(int id) {
        return "远程方法调用超时或者异常错误:id=" + id;
    }

    private String paymentInfo_GlobalFallbackHandler() {
        return "全局错误";
    }

    @GetMapping("/consumer/payment/hystrix/exception")
//    @HystrixCommand
    public String payment_Exception() {
        String result = paymentHystrixService.payment_Exception();
        return result;
    }
}
