package com.djl.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.djl.springcloud.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author djl
 * @create 2021/1/7 14:52
 */
@RestController
@Slf4j
public class FlowLimitController {

    /**
     * 模拟普通QPS流控
     *
     * @return
     */
    @GetMapping("/testA")
    public String testA() {
        return "----testA";
    }

    /**
     * 默认关联流控,例如 /testA 采取了流控关联方式 关联了: /testB , 那么 /testB 达到了流控阈值则 /testA 会受到影响
     *
     * @return
     */
    @GetMapping("/testZ")
    public String testZ() {
        return "----testZ";
    }

    /**
     * 模拟线程阈值控制
     *
     * @return
     */
    @GetMapping("/testB")
    public String testB() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "----testB";
    }

    /**
     * 测试链路流控规则
     */
    @Autowired
    private OrderService orderService;

    /**
     * 设置资源:getOrder使用链路流控配置入口:/testAA,结果效果失效???原因不明
     *
     * @return
     */
    @GetMapping("/testAA")
    public String testAA() {
        orderService.testGetOrder();
        return "----testAA";
    }

    @GetMapping("/testBB")
    public String testBB() {
        orderService.testGetOrder();
        return "----testBB";
    }


    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testD 测试RT");
        return "----testD";
    }


    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10 / 0;
        return "----testE 测试异常数";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "----testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception) {
        return "----deal_testHotKey, o(╥﹏╥)o"; // sentinel的默认提示都是： Blocked by Sentinel (flow limiting)
    }
}
