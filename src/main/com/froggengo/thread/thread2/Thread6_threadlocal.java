package com.froggengo.thread.thread2;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class Thread6_threadlocal {


    private static ThreadLocal<LocalDateTime> THREADLOCAL_TIME = new ThreadLocal();
    private static ThreadLocal<Long> THREADLOCAL_MS = new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        Thread6_threadlocal threadlocal = new Thread6_threadlocal();
        threadlocal.begin();
        Thread.sleep(10_000);
        threadlocal.end();
    }

    @Test
    public void test22() throws InterruptedException {
        Thread6_threadlocal threadlocal = new Thread6_threadlocal();
        threadlocal.beginMs();
        Thread.sleep(10_000);
        System.out.println(threadlocal.endMs());
    }
    void begin() {
        THREADLOCAL_TIME.set(LocalDateTime.now());
    }

    long beginMs() {
        long value = System.currentTimeMillis();
        THREADLOCAL_MS.set(value);
        return value;
    }

    void end() {
        System.out.println(Duration.between(THREADLOCAL_TIME.get(), LocalDateTime.now()));
    }

    long endMs() {
        long value = System.currentTimeMillis();
        long delta = value - THREADLOCAL_MS.get();
//        System.out.println(delta);
        return delta;
    }
}
