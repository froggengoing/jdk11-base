package com.froggengo.thread.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author fly
 * @create 2024-06-06-14:58
 **/
public class T5_CompletableFuture {
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    @Test
    public void test9() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> c1 = newCompleteFuture();
        CompletableFuture<Void> c2 = newCompleteFuture();
        CompletableFuture<Void> c3 = newCompleteFuture();
        CompletableFuture<Void> c4 = newCompleteFuture();
        CompletableFuture<Void> finalfuture = CompletableFuture.allOf(c1, c2, c3, c4);
        System.out.println(finalfuture.get());
    }


    private CompletableFuture<Void> newCompleteFuture() {
        CompletableFuture<Void> c1 = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " begin");
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " end");
            // CompletableFuture默认使用ForkJoinPool,全局公用
        }, poolExecutor);
        return c1;
    }
}
