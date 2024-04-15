package com.froggengo.thread.thread;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author fly
 * @create 2024-04-15-15:49
 **/
public class T10_ThreadNotify {

    /**
     * 这里不能使用test
     *
     * @param args
     */
    //    @Test
    public static void main(String[] args) {
        Worker worker = new Worker();

        Thread p1 = new Thread(() -> {
            while (true) {
                worker.product(UUID.randomUUID().toString());
            }
        });
        p1.start();
        Thread c1 = new Thread(() -> {
            while (true) {
                worker.consume();
            }
        });
        c1.start();

    }

    static class Worker {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

        int size = 10;
        Random random = new Random();

        public void product(String name) {

            synchronized (queue) {
                if (queue.size() >= size) {
                    try {
                        System.out.println("队列已经满了：" + queue.size());
                        queue.wait();
                        System.out.println("product线程被唤醒：" + queue.size());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                queue.add(name + "-" + random.nextInt(1000));

                System.out.println("product：" + name);
                queue.notify();

            }
        }

        public void consume() {

            synchronized (queue) {
                if (queue.isEmpty()) {
                    try {
                        System.out.println("队列已空：" + queue.size());
                        queue.wait();
                        System.out.println("consume线程被唤醒" + queue.size());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                String name = queue.poll();
                System.out.println("consumer:" + name);
                queue.notify();

            }
        }

    }
}
