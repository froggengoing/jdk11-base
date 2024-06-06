package com.froggengo.thread.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author fly
 * @create 2024-06-03-19:09
 **/
public class T3_ThreadPoolException {
    /**
     * poolExecutor.execute(Runnable command) 如果Runnable异常，会导致该线程退出
     * 因此可能导致不断创建线程的问题
     *
     * @param args
     */
    public static void main(String[] args) {
        ThreadFactory threadFactory = newThreadFactory();

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
            poolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                int cal = 1 / 0;
                //    不放开注释         放开注释
                //    Thread-0       Thread-0
                //    Thread-0       Thread-1
                //    Thread-0       Thread-2
                //    Thread-0       Thread-3
                //    Thread-0       Thread-4

            });
        }
    }

    private static ThreadFactory newThreadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        // 处理异常的逻辑
                        System.out.println("线程" + t.getName() + "出现异常：" + e.getMessage());
                    }
                });
                return thread;
            }
        };
        return threadFactory;
    }

    /**
     * submit()会封装为futuretask，如果task由异常，会设置在future内，get的时候跑车
     * @see  ThreadPoolExecutor#runWorker(ThreadPoolExecutor.Worker) 捕获不到异常
     */
    @Test
    public void test58(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                newThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        Future<?> submit = poolExecutor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            int cal = 1 / 0;
        });
        try {
            submit.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
