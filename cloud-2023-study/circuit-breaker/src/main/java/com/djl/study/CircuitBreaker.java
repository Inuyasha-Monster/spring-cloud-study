package com.djl.study;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.djl.study.State.*;

/**
 * 简易断路器的实现
 *
 * @author djl
 */
public class CircuitBreaker {

    private State state;

    private Config config;

    private Counter counter;

    /**
     * 最近一次断路器的打开时间
     */
    private long lastOpenedTime;

    public CircuitBreaker(Config config) {
        this.counter = new Counter(config.getFailureCount(), config.getFailureTimeInterval());
        this.state = CLOSED;
        this.config = config;
    }

    public <T> T run(Supplier<T> toRun, Function<Throwable, T> fallback) {
        try {
            if (state == OPEN) {
                // 判断 half-open 是否超时
                if (halfOpenTimeout()) {
                    // 进入半开逻辑
                    return halfOpenHandle(toRun, fallback);
                }
                // 直接降级处理
                return fallback.apply(new DegradeException("degrade by circuit breaker"));
            } else if (state == CLOSED) {
                T result = toRun.get();
                closed();
                return result;
            } else {
                return halfOpenHandle(toRun, fallback);
            }
        } catch (Exception e) {
            // 增加错误次数且内部计算了时间窗口
            counter.incrFailureCount();
            if (counter.failureThresholdReached()) { // 错误次数达到阀值，进入 open 状态
                open();
            }
            return fallback.apply(e);
        }
    }

    private <T> T halfOpenHandle(Supplier<T> toRun, Function<Throwable, T> fallback) {
        try {
            halfOpen(); // closed 状态超时进入 half-open 状态
            T result = toRun.get();
            int halfOpenSuccCount = counter.incrSuccessHalfOpenCount();
            if (halfOpenSuccCount >= this.config.getHalfOpenSuccessCount()) { // half-open 状态成功次数到达阀值，进入 closed 状态
                closed();
            }
            return result;
        } catch (Exception e) {
            // half-open 状态发生一次错误进入 open 状态
            open();
            return fallback.apply(new DegradeException("degrade by circuit breaker"));
        }
    }

    private boolean halfOpenTimeout() {
        return System.currentTimeMillis() - lastOpenedTime > config.getHalfOpenTimeout();
    }

    private void closed() {
        counter.reset();
        state = CLOSED;
    }

    private void open() {
        state = OPEN;
        lastOpenedTime = System.currentTimeMillis();
    }

    private void halfOpen() {
        state = HALF_OPEN;
    }

    public static void main(String[] args) {
        final Config config = new Config();
        config.setFailureCount(2);
        CircuitBreaker circuitBreaker = new CircuitBreaker(config);
        int degradeCount = 0;
        for (int i = 0; i < 10; i++) {
            final String result = circuitBreaker.run(() -> {
                int n = 1 / 0;
                return "deep in spring cloud";
            }, ex -> {
                if (ex instanceof DegradeException) {
                    return "degrade";
                }
                return "boom";
            });
            System.out.println("result = " + result);
            if ("degrade".equals(result)) {
                degradeCount++;
            }
        }
        System.out.println("degradeCount = " + degradeCount);
    }
}
