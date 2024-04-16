package com.froggengo.thread.thread2;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Thread5_ReentrantReadWriteLock {
    private final Map<String, String> m = new TreeMap<String, String>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();    //读锁
    private final Lock w = rwl.writeLock();    //写锁

    public static void main(String[] args) {
        int SHARED_SHIFT = 16;
        int SHARED_UNIT = (1 << SHARED_SHIFT);
        int MAX_COUNT = (1 << SHARED_SHIFT) - 1;
        int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;
        System.out.println(SHARED_UNIT);
        System.out.println(MAX_COUNT);
        System.out.println(EXCLUSIVE_MASK);
        System.out.println(3 >> SHARED_SHIFT);
    }

    public String get(String key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public String[] allKeys() {
        r.lock();
        try {
            return (String[]) m.keySet().toArray();
        } finally {
            r.unlock();
        }
    }

    public String put(String key, String value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }

    @Test
    public void test63() throws InterruptedException {
        Random random = new Random();
        Runnable task = () -> {
            w.lock();
            int i = random.nextInt(20000);
            String value = String.valueOf(i);
            m.put(value, value);
            System.out.println("put：" + value);
            w.unlock();
        };


        ExecutorService executor = Executors.newFixedThreadPool(10);
        ExecutorService executor2 = Executors.newFixedThreadPool(10);
        Runnable task2 = () -> {
            r.lock();
            int i = random.nextInt(20000);
            String value = m.get(String.valueOf(i));
            System.out.println("read：" + value);
            r.unlock();
        };

        for (int i = 0; i < 1000000; i++) {
            executor.execute(task);
            executor2.execute(task2);
        }

        executor.execute(task2);
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        System.out.println(m.size());
    }
}
