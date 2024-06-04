package com.froggengo.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-06-03-19:09
 **/
public class T3_ThreadPoolException {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        // 处理异常的逻辑
                        System.out.println("线程" + t.getName() + "出现异常：" + e.getMessage());
                    }
                });
                return thread;
            }
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            poolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                int cal = 1 / 0;
                //    不放开注释         放开注释
                //    Thread-0       Thread-0
                //    Thread-0       Thread-1
                //    Thread-0       Thread-2
                //    Thread-0       Thread-3
                //    Thread-0       Thread-4

            });
        }
    }
}
