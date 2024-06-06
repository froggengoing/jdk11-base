package com.froggengo.thread.threadpool;

import java.util.concurrent.*;

/**
 * @author fly
 * @create 2024-06-04-10:53
 **/
public class T0_ThreadPoolCreate {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        /**
         *         return new ThreadPoolExecutor(nThreads, nThreads,
         *                                       0L, TimeUnit.MILLISECONDS,
         *                                       new LinkedBlockingQueue<Runnable>());
         */
        ExecutorService executor1 = Executors.newFixedThreadPool(1);
        /**
         *         return new FinalizableDelegatedExecutorService
         *             (new ThreadPoolExecutor(1, 1,
         *                                     0L, TimeUnit.MILLISECONDS,
         *                                     new LinkedBlockingQueue<Runnable>()));
         */
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        /**
         *         return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
         *                                       60L, TimeUnit.SECONDS,
         *                                       new SynchronousQueue<Runnable>());
         */
        ExecutorService executor3 = Executors.newCachedThreadPool();
        /**
         *
         */
        ExecutorService executor4 = Executors.newScheduledThreadPool(1);
        ScheduledExecutorService executor5 = Executors.newSingleThreadScheduledExecutor();
    }
}
