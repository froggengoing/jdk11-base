package com.froggengo.thread.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author fly
 * @create 2024-04-15-14:49
 **/
public class T3_ThreadNameAndDaemon {
    @Test
    public void testName(){
        IntStream.range(1,5).boxed().map(i->new Thread(()->{
            System.out.println(Thread.currentThread().getName());
        })).forEach(Thread::start);
    }

    //@Test
    public static void main(String[] arg) throws InterruptedException {
        System.out.println(Thread.currentThread().isDaemon());
        Thread thread = new Thread(() -> {
            try {
                System.out.println("当前线程："+Thread.currentThread().getName()+
                        ", 是否为守护线程："+Thread.currentThread().isDaemon());
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        thread.setDaemon(true);
        thread.start();
        Thread.sleep(100);
        System.out.println("设置thread的daemon为true后，立即结束");
    }
}
