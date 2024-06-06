package com.froggengo.thread.lock;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-06-06-12:52
 **/
public class T4_Semaphore {
    @Test
    public void test11() {
        Semaphore lock = new Semaphore(2);

        ExecutorService executors = Executors.newFixedThreadPool(10);
        AtomicInteger count = new AtomicInteger();
        Collection<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            int i1 = count.incrementAndGet();
            Callable<Integer> submit = () -> {
                System.out.println(Thread.currentThread().getName() + " try");
                try {
                    lock.acquire();
                    System.out.println(Thread.currentThread().getName() + " doing");
                    Thread.sleep(1_000);
                    lock.release();
                    System.out.println(Thread.currentThread().getName() + " finished");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //if (i1 == 10) {
                //    throw new RuntimeException("i1==10");
                //}
                System.out.println(Thread.currentThread().getName() + "---" + i1);
                return i1;
            };
            list.add(submit);
        }
        try {
            //  when all complete.
            List<Future<Integer>> futureList = executors.invokeAll(list);
            System.out.println("all task finished");
            for (int i = 0; i < futureList.size(); i++) {
                try {
                    System.out.println(futureList.get(i).get());
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
