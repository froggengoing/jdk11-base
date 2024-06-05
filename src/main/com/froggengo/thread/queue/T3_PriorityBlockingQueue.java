package com.froggengo.thread.queue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author fly
 * @create 2024-06-04-19:46
 **/
public class T3_PriorityBlockingQueue {
    @Test
    public void test12() throws InterruptedException {
        PriorityBlockingQueue<Integer> queue = new PriorityBlockingQueue<>(2);
        queue.put(2);
        queue.put(1);
        queue.put(3);
        print_Thread_state();
        //1
        //2
        //3
        System.out.println(queue.take());
        // 线程waiting
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
    }

    private void print_Thread_state() {
        Thread thread = Thread.currentThread();
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    System.out.println(thread.getState());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setDaemon(true);
        t1.start();
    }
}
