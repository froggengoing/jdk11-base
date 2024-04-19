package com.froggengo.interview;

import java.util.concurrent.locks.ReentrantLock;

/**
 * https://www.bilibili.com/video/BV1ez421C7Fz/?p=1&spm_id_from=pageDriver
 * @author fly
 * @create 2024-04-19-0:02
 **/
public class T1_PrintSerialByThread2 extends Thread {
    static int index;
    static int maxCount;

    static int count;

    static ReentrantLock lock = new ReentrantLock(true);

    private int assign;

    private Runnable action;

    public T1_PrintSerialByThread2(int assign, Runnable action) {
        this.assign = assign;
        if (assign == index) {
            this.assign = 0;
        }
        this.action = action;
    }

    public static void main(String[] args) {
        T1_PrintSerialByThread2.index = 20;
        T1_PrintSerialByThread2.maxCount = 10000;
        T1_PrintSerialByThread2.count = 1;

        for (int i = 0; i < T1_PrintSerialByThread2.index; i++) {
            int finalI = i;
            new T1_PrintSerialByThread2(finalI, () -> {
                System.out.println("A" + finalI+":"+T1_PrintSerialByThread2.count);
            }).start();
        }
    }

    /**
     * 用ReentrantLock、以及双重判断
     */
    @Override
    public void run() {
        while (count <= maxCount) {
            lock.lock();
//            这里的count <= maxCount实际上时双重判断保证了准确性，
//            因为while (count <= maxCount)可能线程安全问题同时进入等待
            if (count <= maxCount && count % index == assign) {
                this.action.run();
                count++;
            }
            lock.unlock();
        }
    }
}
