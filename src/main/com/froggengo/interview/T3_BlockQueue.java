package com.froggengo.interview;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author fly
 * @create 2024-04-16-16:20
 * https://javaguide.cn/java/collection/java-collection-questions-01.html#%E8%AF%B4%E4%B8%80%E8%AF%B4-priorityqueue
 * @see BlockingQueue
 *
 **/
public class T3_BlockQueue {


    static int count;

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<Integer>(10);

        new Thread(() -> {
            try {
                while (true) {
                    System.out.println("begin put：" + count);
                    arrayBlockingQueue.put(count++);
                    System.out.println("finish put：" + count);

                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("begin consume");
                    System.out.println(arrayBlockingQueue.take());
                    System.out.println("finished consume");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }).start();
    }
}
