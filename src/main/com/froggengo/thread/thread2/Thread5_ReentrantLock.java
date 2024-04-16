package com.froggengo.thread.thread2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Thread5_ReentrantLock {
    static int count;

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();//公平锁
        System.out.println("nihao");
        lock.unlock();
        ReentrantLock unfairlock = new ReentrantLock(false);
        //tryLock()// 尝试获取锁,立即返回获取结果 轮询锁
        //tryLock(long timeout, TimeUnit unit)//尝试获取锁,最多等待 timeout 时长 超时锁
        lock.tryLock();
        System.out.println("nihao");
        lock.unlock();
        Thread.currentThread().join();
    }

    /**
     * 线程不安全，打印count值每次运行结果不一样
     *
     * @throws InterruptedException
     */
    @Test
    public void test7() throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock();
        Runnable task = () -> {
//            lock.lock();
            if (count < 1000) {
                count++;
            }
//            lock.unlock();
        };

        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        System.out.println(count);
    }

    /**
     * 使用ReentrantLock上锁保证安全
     * ReentrantLock可重入锁，lock几次就要unlock几次
     *
     * @throws InterruptedException
     */
    @Test
    public void testLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Runnable task = () -> {
            lock.lock();
//            ReentrantLock可重入锁，lock几次就要unlock几次
            lock.lock();
            if (count < 1000) {
                count++;
            }
            lock.unlock();
            lock.unlock();
        };

        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        System.out.println(count);
    }

}

