package com.djl.springcloud.service;

import com.djl.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author djl
 * @create 2020/12/31 13:43
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
