package com.froggengo.thread.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author fly
 * @create 2024-06-06-14:18
 **/
public class T4_FutureTask {
    @Test
    public void test9() throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(10);
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            Thread.sleep(5_000);
            System.out.println(Thread.currentThread().getName() + "finished task ");
            return 1;
        });

        executors.execute(() -> {
            Integer integer = null;
            try {
                System.out.println(Thread.currentThread().getName() + "start to get");
                integer = futureTask.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":" + integer);
        });
        executors.execute(() -> {
            Integer integer = null;
            try {
                System.out.println(Thread.currentThread().getName() + "start to get");
                /**
                 * @see FutureTask#set(Object)
                 * @see FutureTask#finishCompletion()
                 * futureTask任务执行完成后，会唤醒futureTask中waiters单向链
                 */
                integer = futureTask.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":" + integer);
        });
        executors.execute(futureTask);
        executors.awaitTermination(10, TimeUnit.SECONDS);
    }
}
