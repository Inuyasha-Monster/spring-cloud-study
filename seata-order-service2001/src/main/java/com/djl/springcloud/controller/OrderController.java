package com.djl.springcloud.controller;

import com.djl.springcloud.entity.CommonResult;
import com.djl.springcloud.entity.Order;
import com.djl.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className: OrderController
 * @description:
 * @author: djl
 * @create: 2020-06-12 10:28
 */

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;


    @GetMapping("/order/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建成功");
    }
}
