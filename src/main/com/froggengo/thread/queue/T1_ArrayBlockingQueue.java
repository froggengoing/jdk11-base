package com.froggengo.thread.queue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-06-04-12:31
 **/
public class T1_ArrayBlockingQueue {
    /**
     * 直接抛异常
     * java.lang.IllegalStateException: Queue full
     */
    @Test
    public void test13_add() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        //queue.add(null);
        queue.add(12);
        queue.add(13);
        queue.add(14);
    }

    /**
     * 不抛异常，只返回false
     */
    @Test
    public void test24_offer() {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        System.out.println(queue.offer(12));
        System.out.println(queue.offer(13));
        System.out.println(queue.offer(14));
    }

    /**
     * put方法会引起线程阻塞
     */
    @Test
    public void test11_put() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.put(12);
        System.out.println("remainingCapacity:" + queue.remainingCapacity());
        queue.put(13);
        System.out.println("remainingCapacity:" + queue.remainingCapacity());
        print_Thread_state();
        // 导致线程 WAITING 状态
        queue.put(14);
    }

    @Test
    public void test52_put_exception() throws InterruptedException {
        BlockingQueue<Object> queue = new PriorityBlockingQueue<>();
        queue.offer(new Object());
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

    @Test
    public void test59_offer_timeout() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        System.out.println(queue.offer(12, 5, TimeUnit.SECONDS));
        System.out.println(queue.offer(13, 5, TimeUnit.SECONDS));
        long begin = System.currentTimeMillis();
        boolean offer = queue.offer(14, 5, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis() - begin + ":" + offer);
    }

    /**
     * java.util.NoSuchElementException
     */
    @Test
    public void test72_remove() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.remove();
    }

    @Test
    public void test82_poll() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    @Test
    public void test82_take() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.put(1);
        print_Thread_state();
        System.out.println(queue.take());
        // 线程waiting
        System.out.println(queue.take());
    }

    @Test
    public void test82_poll_timeout() throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.add(1);
        print_Thread_state();
        // TIMED_WAITING
        System.out.println(queue.poll(5, TimeUnit.SECONDS));
        // 线程waiting
        System.out.println(queue.poll(5, TimeUnit.SECONDS));
    }


}
