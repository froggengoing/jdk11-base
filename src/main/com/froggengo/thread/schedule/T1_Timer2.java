package com.froggengo.thread.schedule;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-06-06-16:05
 **/
public class T1_Timer2 {

    /**
     * schedule 则 上一次时间 + peroid
     * scheduleAtFixedRate 执行时间固定为 start_time + n * priod
     */
    @Test
    public void test12() throws InterruptedException {
        Timer timer = new Timer();
        long beginMs = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        //(任务耗时：3s)第一次： 1000
        //(任务耗时：0.5s)第二次： 1000 * 2000*1，但任务耗时3秒，所以执行开始时间为 4000,即第一次执行完马上执行
        //第三次：
        // scheduleAtFixedRate ，则开始时间为 1000 * 2000*2, => 第一次开始时间 + （n-1）* period
        // schedule，则为 4000 + task cost / period，=> 上一次运行时间 +  period
        /** scheduleAtFixedRate          schedule
         *  1008:Timer-0                1016:Timer-0
         *  4037:Timer-0                4039:Timer-0
         *  5004:Timer-0               6043:Timer-0
         *
         *  scheduleAtFixedRate: 更强调原来的计划
         *  schedule：更强调 任务执行的间隔
         *
         */
        timer.scheduleAtFixedRate(newTimeTask(beginMs, count), 1000L, 2000L);

        Thread.sleep(20_000);
    }

    private TimerTask newTimeTask(long beginMs, AtomicInteger count) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println(System.currentTimeMillis() - beginMs + ":" +
                            Thread.currentThread().getName());
                    if (count.getAndIncrement() == 0) {
                        Thread.sleep(3_000);
                    } else {
                        Thread.sleep(5_00);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return task;
    }
}
