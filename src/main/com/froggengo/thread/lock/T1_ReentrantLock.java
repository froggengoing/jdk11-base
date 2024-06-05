package com.froggengo.thread.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fly
 * @create 2024-06-05-15:20
 **/
public class T1_ReentrantLock {
    // 线程不安全示例
    @Test
    public void test9() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Stat stat = new Stat();
        int count = 10000;
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                stat.count = stat.count + 1;
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(stat.count);
    }

    //线程安全示例
    @Test
    public void test10() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        //executorService.prestartAllCoreThreads();
        Stat stat = new Stat();
        int count = 10000;
        ReentrantLock lock = new ReentrantLock(true);
        CountDownLatch latch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                lock.lock();
                stat.count = stat.count + 1;
                System.out.println(Thread.currentThread().getName() + ":" + stat.count);
                lock.unlock();
                latch.countDown();
            });
        }
        latch.await();
        System.out.println(stat.count);
    }

    @Test
    public void test56_acquire() {
        ReentrantLock lock = new ReentrantLock(true);
        Thread t1 = newThread(lock);
        Thread t2 = newThread(lock);
        t1.start();
        /**
         * debug
         * @see AbstractQueuedSynchronizer#acquire(int)
         */

        t2.start();
    }

    private Thread newThread(ReentrantLock lock) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        });
        return t1;
    }


    static class Stat {
        int count;
    }
}
