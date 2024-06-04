package com.froggengo.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-06-03-16:17
 **/
public class T2_ThreadPoolPrestart {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        System.out.println("getActiveCount before pre start:" + poolExecutor.getPoolSize());
        // 预热，提前创建线程池core核心数量
        poolExecutor.prestartAllCoreThreads();
        System.out.println("getActiveCount after pre start:" + poolExecutor.getPoolSize());
    }
}
