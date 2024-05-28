package com.froggengo.ratelimit;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * https://developer.aliyun.com/article/1259461#slide-2
 * @author fly
 * @create 2024-05-21-22:07
 **/
public class BucketRateLimiter {
    private long capacity; // 桶的容量
    private long rate; // 每秒的令牌生成速率
    private AtomicLong water;// 桶中的令牌数量
    private long lastRefillTime;// 上次令牌生成时间

    public BucketRateLimiter(long capacity, long rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.water = new AtomicLong(0);
        this.lastRefillTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        BucketRateLimiter bucketRateLimiter = new BucketRateLimiter(10, 2);
        for (int i = 0; i < 20; i++) {
            // 模拟200个请求
            if (bucketRateLimiter.tryAcquire(1)) {
                System.out.println("处理请求 " + i);
            } else {
                System.out.println("请求 " + i + " 被限流");
            }
            TimeUnit.MILLISECONDS.sleep(100); // 每个请求间隔100毫秒
        }
    }

    public synchronized boolean tryAcquire(long num) {
        // 先漏水
        long now = System.currentTimeMillis();
        long leaked = (now - lastRefillTime) * rate / 1000;
        if (leaked > 0) {
            System.out.println("leaked:" + leaked);
            water.set(Math.max(0, water.get() - leaked));
            lastRefillTime = now;
        }
        // 再加水
        if (num <= capacity - water.get()) {
            water.addAndGet(num);
            return true;
        } else {
            return false;
        }
    }
}
