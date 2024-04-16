package com.froggengo.thread.thread;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fly
 * @create 2024-04-16-14:32
 **/
public class T14_CompleteFuture {

    @Test
    public void test10() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ArrayList<CompletableFuture<?>> list = new ArrayList();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
//            注意这里是supplyAsync
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return finalI;
//                这里最好使用自定义的线程池，否则使用默认的ForkJoinPool
            }, executorService);
            list.add(future);
        }
//        如果你不等待所有CompletableFuture完成，而是直接在遍历list时调用get()方法，那么可能会造成主线程提前退出，导致一些CompletableFuture还未完成就被访问，这会抛出ExecutionException异常。
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(list.toArray(new CompletableFuture[0]));
        allFuture.join();

        for (CompletableFuture<?> future : list) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("const：" + (System.currentTimeMillis() - begin));

    }
}
