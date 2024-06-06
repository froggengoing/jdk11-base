package com.froggengo.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author fly
 * @create 2024-06-05-19:53
 **/
public class T3_Locksupport {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName() + "睡眠");
            LockSupport.park(thread);
            System.out.println(thread.getName() + "被唤醒");
        });
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName() + "唤醒" + t1.getName());
        LockSupport.unpark(t1);
    }
}
