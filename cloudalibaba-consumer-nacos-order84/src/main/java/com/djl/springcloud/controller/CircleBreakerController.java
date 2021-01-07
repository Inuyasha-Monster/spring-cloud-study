package com.djl.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import com.djl.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author djl
 * @create 2021/1/7 17:42
 */
@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 特别注意哈:
     * 1.如果是url限流如果被限流默认返回的东西是:Blocked by Sentinel (flow limiting)
     * 2.如果是 SentinelResource 限流如果被限流的话默认是返回一个异常:java.lang.reflect.UndeclaredThrowableException
     *
     * @return
     */
    @GetMapping("/testA")
    @SentinelResource("testA")
    public String testA() {
        return "----testA";
    }


    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback") // 默认直接配置达到限流条件引发异常
//    @SentinelResource(value = "fallback", fallback = "handlerFallback") // 使用自定义降级方法
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler") // 使用自定义流控回退方法
//    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler") // 使用自定义降级方法+使用自定义流控回退方法
    @SentinelResource(value = "fallback", fallback = "handlerFallback", blockHandler = "blockHandler",
            exceptionsToIgnore = {IllegalArgumentException.class}) // 使用自定义降级方法+使用自定义流控回退方法+自定义忽略异常不走fallback方法
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);

        if (id == 4) {
            throw new IllegalArgumentException("IllegalArgument ,非法参数异常...");
        } else if (result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }

        return result;
    }

    public CommonResult handlerFallback(@PathVariable Long id, Throwable e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult(444, "异常handlerFallback，exception内容： " + e.getMessage(), payment);
    }

    public CommonResult blockHandler(@PathVariable Long id, BlockException e) {
        Payment payment = new Payment(id, "null");
        return new CommonResult(444, "blockHandler-sentinel 限流，BlockException： " + e.getMessage(), payment);
    }

    //======= OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentService.paymentSQL(id);
    }

}