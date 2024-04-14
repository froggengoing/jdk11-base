package com.froggengo.thread.threadpool;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-04-08-18:27
 **/
public class T1_ThreadPoolStatusChange {

    /**
     * 这里测试线程的shutdown()和shutdownNow()
     * shutdown()会等等所有任务包括队列中执行完成
     * shutdownNow()则直接终止任务，但是是通过Thread.interrupt()方法实现的，如果任务里面没有sleep 、wait、Condition等是无法终止任务的
     * 如newTask()中是计算，则任务能正常执行万，而newTaskWithSleep则抛异常java.lang.InterruptedException
     * @see java.util.concurrent.ThreadPoolExecutor#interruptWorkers()
     * shutdownNow -> 状态STOP
     * shutdown    -> 状态SHUTDOWN
     * 两个状态的区别体现在
     * 1. 线程执行任务时发现是stop，直接终止Thread.interrupted()
     * @see java.util.concurrent.ThreadPoolExecutor#runWorker(java.util.concurrent.ThreadPoolExecutor.Worker)
     * 2.
     */
    @Test
    public void test17() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), new ThreadPoolExecutor.CallerRunsPolicy());
        Runnable runnable = newTaskWithSleep(executor);
        executor.execute(runnable);
        executor.execute(runnable);
        printExecutor("main", executor);

        Thread.sleep(1000);

        printStatus("before shutdown", executor);
        executor.shutdown();
        printStatus("after shutdown", executor);
//        shutDown后尝试再添加任务，会走reject处理器，一般再reject处理器中都会判断线程是否shutDown
        executor.execute(runnable);
        Thread.sleep(20_000);
        printStatus("after shutdown delay", executor);
        System.out.println("finished");
    }

    private Runnable newTask(ThreadPoolExecutor executor) {
        AtomicInteger index = new AtomicInteger(0);
        //任务
        Runnable runnable = () -> {
            try {
                int cur = index.incrementAndGet();
                long begin = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " begin index:" + cur);
                printExecutor("pool" + cur, executor);
//                Thread.currentThread().sleep(5000);
                long count = 0;
                long max = Integer.MAX_VALUE * 5L;
                while (count < max) {
                    count++;
                }
                System.out.println(Thread.currentThread().getName() + " end" + cur + ",cost:" + (System.currentTimeMillis() - begin));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return runnable;
    }
    private Runnable newTaskWithSleep(ThreadPoolExecutor executor) {
        AtomicInteger index = new AtomicInteger(0);
        //任务
        Runnable runnable = () -> {
            try {
                int cur = index.incrementAndGet();
                long begin = System.currentTimeMillis();
                System.out.println(Thread.currentThread().getName() + " begin index:" + cur);
                printExecutor("pool" + cur, executor);
                Thread.currentThread().sleep(5000);
                System.out.println(Thread.currentThread().getName() + " end" + cur + ",cost:" + (System.currentTimeMillis() - begin));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return runnable;
    }
    private void printExecutor(String type, ThreadPoolExecutor executor) {
        System.out.println(type + " " + "monitor executor:" + executor.toString());
    }

    private void printStatus(String comment, ThreadPoolExecutor executor) {
        System.out.println(comment + "," + "monitor executor,count:" + executor.getActiveCount());
        System.out.println(comment + "," + "monitor executor,queue:" + executor.getQueue().size());
        System.out.println(comment + "," + "monitor executor,status isShutdown:" + executor.isShutdown());
        System.out.println(comment + "," + "monitor executor,status isTerminating:" + executor.isTerminating());
        System.out.println(comment + "," + "monitor executor,status isTerminated:" + executor.isTerminated());
    }
}
