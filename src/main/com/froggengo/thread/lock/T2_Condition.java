package com.froggengo.thread.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fly
 * @create 2024-06-05-18:29
 **/
public class T2_Condition {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        AbstractQueuedSynchronizer.ConditionObject condition = (AbstractQueuedSynchronizer.ConditionObject) lock.newCondition();
        AtomicInteger count = new AtomicInteger();
        Thread t0 = newThread(lock, condition, count, 0);
        //Thread t1 = newThread(lock, condition, count, 1);
        //Thread t2 = newThread(lock, condition, count, 2);
        t0.start();
        //t1.start();
        //t2.start();
        Thread.sleep(30 * 1000);
        lock.lock();

        condition.signal();
        lock.unlock();
        //Thread.sleep(20 * 1000);
        System.out.println("finished");
    }

    private static Thread newThread(ReentrantLock lock, Condition condition, AtomicInteger count, int i) {
        return new Thread(() -> {
            while (count.get() < 20) {
                lock.lock();
                if (count.get() % 2 == i) {
                    System.out.println("线程" + i + "：" + count.getAndIncrement());
                }
                try {
                    condition.signal();
                    // bug 这必须在判断一次
                    if (count.get() < 20) {
                        System.out.println("线程" + i + "休眠");
                        condition.await();
                    } else {
                        System.out.println("QueueLength:" + lock.getQueueLength());
                        System.out.println("holdCount:" + lock.getHoldCount());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.unlock();
            }
            System.out.println("线程" + i + "：" + "结束");
        });
    }
}
