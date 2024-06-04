package com.froggengo.thread.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-06-04-15:49
 **/
public class T1_MyLinkedTest {
    public static void main(String[] args) throws InterruptedException {
        T2_MyLinkedBlockingQueue queue = new T2_MyLinkedBlockingQueue(10);
        AtomicInteger count = new AtomicInteger();
        Thread producer = product(queue, count);
        Thread consumer1 = consume(queue);
        Thread consumer2 = consume(queue);
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer2.join();
    }

    private static Thread product(BlockingQueue<Integer> queue, AtomicInteger count) {
        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    queue.put(count.incrementAndGet());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        return producer;
    }

    private static Thread consume(BlockingQueue<Integer> queue) {
        Thread consumer1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        return consumer1;
    }
}
