package com.djl.springcloud.service;

import com.djl.springcloud.entities.CommonResult;
import com.djl.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @author djl
 * @create 2021/1/7 17:25
 */
@Component
public class PaymentFallbackService implements PaymentService{

    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(444,"服务降级返回，---PaymentFallbackService",new Payment(id,"ErrorSerial"));
    }
}