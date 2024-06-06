package com.froggengo.thread.lock;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fly
 * @create 2024-06-06-14:48
 **/
public class T5_TaskList_finished {
    @Test
    public void test13() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);


        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Runnable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " begin");
                Thread.sleep(1_000);
                System.out.println(Thread.currentThread().getName() + " end");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        executorService.execute(runnable);
        executorService.execute(runnable);
        long begin = System.currentTimeMillis();
        // 这里会阻塞知道countDown为0
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - begin + "--finished");
    }

    @Test
    public void test40() {
        CountDownLatch countDownLatch = new CountDownLatch(2);


        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable runnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " begin");
                Thread.sleep(1_000);
                System.out.println(Thread.currentThread().getName() + " end");
                return null;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Collection<Callable<Void>> list = List.of(runnable, runnable);
        try {
            long begin = System.currentTimeMillis();
            executorService.invokeAll(list);
            System.out.println(System.currentTimeMillis() - begin + "--finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
