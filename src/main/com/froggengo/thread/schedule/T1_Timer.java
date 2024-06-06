package com.froggengo.thread.schedule;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author fly
 * @create 2024-06-06-15:41
 **/
public class T1_Timer {
    /**
     * 固定延迟后执行一次
     */
    @Test
    public void test9() {
        Timer timer = new Timer();
        long beginMs = System.currentTimeMillis();

        // 任务2的开始执行时间 = 任务1 delay time + task1 cost
        TimerTask t1 = newTimeTask(beginMs);
        timer.schedule(t1, 1000L);
        //任务1优先执行
        TimerTask t2 = newTimeTask(beginMs);
        timer.schedule(t2, 500L);

        try {
            Thread.sleep(5_000);
            t1.cancel();
            Thread.sleep(5_000);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test339() {
        Timer timer = new Timer();
        long beginMs = System.currentTimeMillis();
        // 1s 延迟后执行任务，每秒执行一次
        // 但是任务执行需要两秒，所以这里每次执行完任务后会马上执行下一次任务
        // task cost > period ，马上执行
        TimerTask t1 = newTimeTask(beginMs);
        timer.schedule(t1, 1000L, 1000L);

        try {
            Thread.sleep(20_000);
            System.out.println("准备取消任务");
            t1.cancel();
            System.out.println("取消任务");
            Thread.sleep(20_000);
            System.out.println("finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * schedule 则 上一次时间 + peroid
     */
    @Test
    public void test48() {
        Timer timer = new Timer();
        long beginMs = System.currentTimeMillis();
        // task cost < period 则 为 上次执行开始时间+period
        timer.schedule(newTimeTask(beginMs), 1000L, 3000L);

        try {
            Thread.sleep(20_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * scheduleAtFixedRate 执行时间固定为 start_time + n * priod
     */
    @Test
    public void test62() {
        Timer timer = new Timer();
        long beginMs = System.currentTimeMillis();
        // task cost < period 则 为 上次执行开始时间+period
        timer.scheduleAtFixedRate(newTimeTask(beginMs), 1000L, 1000L);

        try {
            Thread.sleep(20_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private TimerTask newTimeTask(long beginMs) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println(System.currentTimeMillis() - beginMs + ":" +
                            Thread.currentThread().getName());
                    Thread.sleep(2_000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return task;
    }
}
