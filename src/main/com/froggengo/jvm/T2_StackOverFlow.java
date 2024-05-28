package com.froggengo.jvm;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-04-15-14:45
 **/
public class T2_StackOverFlow {

    /**
     * 1、测试栈的最大深度
     * 2、修改启动参数-Xss2M
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            while (true) {
            }
        });
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            add(atomicInteger);
        } catch (Error e) {
            System.out.println(e);
            System.out.println("栈深度：" + atomicInteger.get());
        }
        //默认-Xss1M:10415、16663、16480、10411
        //-Xss2M:35640、34350、21346
    }

    public static void add(AtomicInteger atomicInteger) {
        int andIncrement = atomicInteger.getAndIncrement();
        add(atomicInteger);
    }

    @Test
    /**
     * 不断创建线程直到栈溢出，关注
     * 1、最大的数量以及异常提示
     * 2、不同启动参数的不同
     * 3、很容易死机，清不要轻易尝试
     */
    public void test1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        try {
            while (true) {
                new Thread(() -> {
                    int value = atomicInteger.getAndIncrement();
                    if (value % 1000 == 0) System.out.println("创建线程：" + value);
                    try {
                        TimeUnit.MINUTES.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (Error e) {
            System.out.println("线程计数：" + atomicInteger.get());
        }
    }

}
