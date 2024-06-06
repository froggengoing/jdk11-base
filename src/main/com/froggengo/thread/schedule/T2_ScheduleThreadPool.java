package com.froggengo.thread.schedule;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-06-06-16:31
 **/
public class T2_ScheduleThreadPool {
    ScheduledThreadPoolExecutor scheduledThreadPool = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(2);
    @Test
    public void test9() throws InterruptedException {
        // 这里修改线程数量，不影响结果
        // 即任务执行完才会设置下一次任务执行，所以不会出现同时执行同一个任务的问题

        long beginMs = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        /**
         * scheduleWithFixedDelay：任务间执行时间为delay，即 上一次结束时间+delay
         * 1015:pool-1-thread-1
         * 4033:pool-1-thread-1finished
         * 6063:pool-1-thread-1    每次任务都是在上一次任务结束的基础上 + delay
         * 6577:pool-1-thread-1finished
         * 8582:pool-1-thread-1
         * 9095:pool-1-thread-1finished
         */
        //scheduledThreadPool.scheduleWithFixedDelay(newTimeTask(beginMs, count), 1L, 2L, TimeUnit.SECONDS);

        /**
         * scheduleAtFixedRate : first start time + (n-1) * period
         * 1018:pool-1-thread-1
         * 4044:pool-1-thread-1finished
         * 4051:pool-1-thread-1
         * 4557:pool-1-thread-1finished
         * 5023:pool-1-thread-1   这是按计划执行，没有考虑上一次任务由延迟
         * 5531:pool-1-thread-1finished
         */
        ScheduledFuture<?> scheduledFuture = scheduledThreadPool.scheduleAtFixedRate(newTimeTask(beginMs, count), 1L, 2L, TimeUnit.SECONDS);

        Thread.sleep(20_000);
    }


    private Runnable newTimeTask(long beginMs, AtomicInteger count) {
        Runnable task = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println(System.currentTimeMillis() - beginMs + ":" + name);
                // 这里queue size一直为0
                System.out.println(scheduledThreadPool.getQueue().size() + ":" + name);

                if (count.getAndIncrement() == 0) {
                    Thread.sleep(3_000);
                } else {
                    Thread.sleep(5_00);
                }
                System.out.println(System.currentTimeMillis() - beginMs + ":" + name + "finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        return task;
    }

    @Test
    public void test70(){
        // 创建一个 ScheduledExecutorService
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // 创建一个 Callable 任务
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName() + " executing task");
            // 模拟长时间运行任务
            Thread.sleep(2000);
            return "Task completed";
        };

        // 调度任务，延迟1秒后执行
        ScheduledFuture<String> future = scheduler.schedule(task, 1, TimeUnit.SECONDS);

        // 获取任务的执行结果
        try {
            // get 方法会阻塞，直到任务完成并返回结果
            String result = future.get();
            System.out.println("Task result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            // 捕获任务执行中的异常
            e.printStackTrace();
        } finally {
            // 关闭调度器
            scheduler.shutdown();
        }
    }
}

