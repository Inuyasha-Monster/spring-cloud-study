package com.djl.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author djl
 * @create 2021/1/3 10:03
 */
@Component
public class PaymentHystrixServiceFallbackImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_Timeout(int id) {
        return "自定义:paymentInfo_Timeout 返回";
    }

    @Override
    public String payment_Exception() {
        return "自定义:payment_Exception 返回";
    }
}
