package com.froggengo.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 请将运行环境切换至jdk8，和jdk9/11运行
 * jdk8中，FutureTask.get()方法在completing状态下，会先返回线程中断状态
 * jdk11，则先判断是否有结果，如果有结果则直接返回
 * @author fly
 * @create 2024-04-08-9:48
 * 参考资料
 * https://www.cnblogs.com/thisiswhy/p/13791966.html
 * https://bwm5jmajaa.feishu.cn/wiki/GhfewxHciiWfTikEMCtc15wZnSf
 **/
public class T2_jdk_thread_bug {

    public static void main(String[] args) throws Exception {

        AtomicReference<FutureTask<Integer>> a = new AtomicReference<>();
        Runnable task = () -> {
            while (true) {
                FutureTask<Integer> f = new FutureTask<>(() -> 1);
                a.set(f);
                f.run();
            }
        };

        Supplier<Runnable> observe = () -> () -> {
            while (a.get() == null);

            int c = 0;
            int ic = 0;
            while (true) {
                c++;
                FutureTask<Integer> f = a.get();
                while (!f.isDone()) {}
                try {
                    /*
                    Set the interrupt flag of this thread.
                    The future reports it is done but in some cases a call to
                    "get" will result in an underlying call to "awaitDone" if
                    the state is observed to be completing.
                    "awaitDone" checks if the thread is interrupted and if so
                    throws an InterruptedException.
                     */
                    Thread.currentThread().interrupt();
                    f.get();
                }
                catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
                catch (InterruptedException e) {
                    ic ++;
                    System.out.println("InterruptedException observed when isDone() == true " + c + " " + ic + " " + Thread.currentThread());
                }
            }
        };

        CompletableFuture.runAsync(task);
        Stream.generate(observe::get)
                .limit(Runtime.getRuntime().availableProcessors() - 1)
                .forEach(CompletableFuture::runAsync);

        Thread.sleep(1000);
        System.exit(0);
    }
}
