package com.froggengo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class T1_ThreadPoolLearn {
    /**
     * 示例1：测试向threadPool添加task时，线程池的变化
     * 1. 线程数<coreSize，则直接新建线程运行。这里不考虑线程是否空闲
     * 2. 线程数>coreSize，添加到 WorkQueue，等待线程池getTask()
     * 3. 线程数>coreSize，WorkQueue满了，则新建线程
     * 4. 新建coreSize之外的线程，直接执行任务，所以理论上能处理的任务数量时，coreSize + queueSize + (maxSize - coreSize)
     * 5. 超过了maxsize后，根据拒绝任务的政策拒绝处理方式
     * @see ThreadPoolExecutor.CallerRunsPolicy() 这里时直接在调用线程执行，所以这里可能导致任务更早执行
     * @see ThreadPoolExecutor.AbortPolicy，抛异常
     * @see ThreadPoolExecutor.DiscardPolicy donothing
     * @see ThreadPoolExecutor.DiscardOldestPolicy 丢弃最旧的任务，并重新execute(task)
     */
    @Test
    public void testThreadPool1() {


        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadPoolExecutor.CallerRunsPolicy());
        Runnable runnable = newTask(executor);

        printExecutor("main", executor);
        executor.execute(runnable);
        printExecutor("main", executor);
        System.out.println("===================");
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("===================next test=============");

        executor.execute(runnable);
//        pool size = 2, active threads = 1, queued tasks = 0, completed tasks = 1
        printExecutor("main 1 end，期望，扩大线程，但这里小于等于coreSize：", executor);
        System.out.println("===================");
        executor.execute(runnable);
//        pool size = 2, active threads = 1, queued tasks = 1, completed tasks = 1
        printExecutor("main 2 end，期望：添加到 WorkQueue", executor);
        System.out.println("===================");
        executor.execute(runnable);
//        pool size = 2, active threads = 2, queued tasks = 1, completed tasks = 1
        /**
         * 这里偶发两种情况
         * active threads = 2, queued tasks = 1
         * active threads = 1, queued tasks = 1
         * active threads依赖于worker有没有上锁，上锁依赖于能不能从 workerQueue 拿去task
         * 出现active threads = 1, queued tasks = 1，应该时卡在从 workerQueue 拿出了任务，但是没有上锁
         * @see  java.util.concurrent.ThreadPoolExecutor#runWorker(java.util.concurrent.ThreadPoolExecutor.Worker)
         */
        printExecutor("main 3 end，期望：添加到 WorkQueue", executor);

        System.out.println("===================");
        executor.execute(runnable);
//        pool size = 2, active threads = 2, queued tasks = 2, completed tasks = 1
        printExecutor("main 4 end，期望：添加到 WorkQueue：", executor);
        System.out.println("===================");
        executor.execute(runnable);
//        pool size = 3, active threads = 3, queued tasks = 2, completed tasks = 1
        printExecutor("main 5 end,期望：扩大线程数，线程数量大于coreSize：", executor);
        System.out.println("===================");
        executor.execute(runnable);
        printExecutor("main 6 end,期望：当前线程直接执行：", executor);
        System.out.println("===================");
        executor.execute(runnable);
        printExecutor("main 7 end,期望：当前线程直接执行：", executor);
        System.out.println("===================");
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printExecutor("main end", executor);
        System.out.println("finished");
    }

    private Runnable newTask(ThreadPoolExecutor executor) {
        AtomicInteger index = new AtomicInteger(0);
        //任务
        Runnable runnable = () -> {
            try {
                int cur = index.incrementAndGet();
                System.out.println(Thread.currentThread().getName() + " begin " + cur);
                printExecutor("pool" + cur, executor);
                Thread.currentThread().sleep(2000);
                System.out.println(Thread.currentThread().getName() + " end" + cur);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        return runnable;
    }

    private void printExecutor(String type, ThreadPoolExecutor executor) {
        System.out.println(type + " " + "monitor executor:" + executor.toString());
    }


}
