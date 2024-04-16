package com.froggengo.thread.thread2;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Thread20_CyclicBarrier1 {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()-> {
            System.out.println("优先执行！");
        });
        Runnable runnable = () -> {
            try {
                System.out.println("进入线程:" + Thread.currentThread().getName());
                cyclicBarrier.await();
                System.out.println("等待结束。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } finally {
                System.out.println("退出线程:" + Thread.currentThread().getName());
            }
        };
        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");
        t1.start();
        t2.start();
        cyclicBarrier.await();
        System.out.println("主线程");
    }
}
