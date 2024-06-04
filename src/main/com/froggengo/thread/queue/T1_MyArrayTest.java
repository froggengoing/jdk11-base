package com.froggengo.thread.queue;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-06-04-15:49
 **/
public class T1_MyArrayTest {
    public static void main(String[] args) throws InterruptedException {
        T1_MyArrayBlockingQueue queue = new T1_MyArrayBlockingQueue(10, false);
        AtomicInteger count = new AtomicInteger();
        Thread producer = product(queue, count);
        Thread consumer1 = consume(queue);
        Thread consumer2 = consume(queue);
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer2.join();
    }

    private static Thread product(T1_MyArrayBlockingQueue queue, AtomicInteger count) {
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

    private static Thread consume(T1_MyArrayBlockingQueue queue) {
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
