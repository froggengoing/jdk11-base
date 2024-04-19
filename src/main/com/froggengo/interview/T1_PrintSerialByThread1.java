package com.froggengo.interview;

/**
 * 被人的答案，我觉得有问题
 * @author fly
 * @create 2024-04-18-23:39
 **/
public class T1_PrintSerialByThread1 {
    public static final Object lock = new Object();
    public static final int maxIndex = 100;
    public static int index = 0;

    public static void main(String[] args) {

        new Thread(() -> {
            while (index < maxIndex) {
                synchronized (lock) {
                    //这里导致 index=100时候也进入了wait
                    while (!(index % 2 == 0 && index < 100)) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(1);
                    index++;
                    lock.notifyAll();
                }
            }
        }).start();
        new Thread(() -> {
            while (index < maxIndex) {
                synchronized (lock) {
                    while (!(index % 2 == 1 && index < 100)) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(2);
                    index++;
                    lock.notifyAll();
                }
            }
        }).start();
    }
}
