package com.djl.study;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 断路器的计时器
 *
 * @author djl
 */
public class Counter {
    /**
     * Closed 状态进入 Open 状态的错误个数阀值
     */
    private final int failureCount;

    /**
     * failureCount 统计时间窗口
     */
    private final long failureTimeInterval;

    /**
     * 当前错误次数
     */
    private final AtomicInteger currentCount;

    /**
     * 上一次调用失败的时间戳
     */
    private long lastTime;

    /**
     * Half-Open 状态下成功次数
     */
    private final AtomicInteger halfOpenSuccessCount;

    public Counter(int failureCount, long failureTimeInterval) {
        this.failureCount = failureCount;
        this.failureTimeInterval = failureTimeInterval;
        this.currentCount = new AtomicInteger(0);
        this.halfOpenSuccessCount = new AtomicInteger(0);
        this.lastTime = System.currentTimeMillis();
    }

    /**
     * 增加失败次数
     *
     * @return 当前错误次数
     */
    public synchronized int incrFailureCount() {
        long current = System.currentTimeMillis();
        if (current - lastTime > failureTimeInterval) {
            // 超过时间窗口，当前失败次数重置为 0
            lastTime = current;
            currentCount.set(0);
        }
        return currentCount.getAndIncrement();
    }

    /**
     * 增加半开状态的成功次数
     *
     * @return
     */
    public int incrSuccessHalfOpenCount() {
        return this.halfOpenSuccessCount.incrementAndGet();
    }

    /**
     * 判断是否错误次数超限
     *
     * @return
     */
    public boolean failureThresholdReached() {
        return getCurCount() >= failureCount;
    }

    public int getCurCount() {
        return currentCount.get();
    }

    /**
     * 重置指标
     */
    public synchronized void reset() {
        halfOpenSuccessCount.set(0);
        currentCount.set(0);
    }
}
