package com.djl.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.micrometer.core.instrument.Meter;
import org.springframework.stereotype.Service;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @author djl
 * @create 2021/1/3 9:00
 */
@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfo_OK,id:  " + id + " O(∩_∩)O哈哈";
    }

    // ====== 服务降级

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            })
    public String paymentInfo_Timeout(Integer id) {
        int timeNum = 3;
        try {
            TimeUnit.SECONDS.sleep(timeNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfo_Timeout,id:  " + id + " O(∩_∩)O哈哈" + "  耗时秒钟:" + timeNum;
    }

    public String paymentInfo_TimeoutHandler(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfo_TimeoutHandler,id:  " + id + " 超时了";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_ExceptionHandler",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            })
    public String payment_Exception() {
        int i = 10 / 0;
        return "ok";
    }

    public String paymentInfo_ExceptionHandler() {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfo_ExceptionHandler 出错了";
    }


    // ====== 服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName() + "\t" + "调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " + id;
    }


}
