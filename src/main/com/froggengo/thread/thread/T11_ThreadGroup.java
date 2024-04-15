package com.froggengo.thread.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-04-15-16:34
 **/
public class T11_ThreadGroup {

    @Test
    public void test10() {
        Thread t1 = new Thread("t1");
        System.out.println("主线程threadGroup：" + Thread.currentThread().getName());
        System.out.println("默认threadGroup：" + t1.getThreadGroup().getName());
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread thread = new Thread(tg1, () -> {
        }, "t2");
        System.out.println("指定threadGroup：" + thread.getThreadGroup().getName());
    }

    @Test
    public void test (){
        ThreadGroup tg = new ThreadGroup("tg1");
        Thread t1 = new Thread(tg,()->{
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1");
        Thread t2 = new Thread(tg,()->{
            try {
                TimeUnit.MINUTES.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2");
        t1.start();
        t2.start();
        Thread[] tga=new Thread[tg.activeCount()];
        int enumerate = tg.enumerate(tga);
        for (Thread thread : tga) {
            System.out.println("新建线程组:"+thread.getName());
        }
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        //threadGroup.activeCount()返回所active的线程，包括子线程组的线程
        Thread[] tgm=new Thread[threadGroup.activeCount()];
        //是否递归获取子线程组的线程
        //因为activeCount返回是包含了子线程组的活动线程数的
        //设置recurse为false不递归获取，导致数组部分为null
        int enumerate1 = threadGroup.enumerate(tgm,true);
        for (Thread thread : tgm) {
            if(thread!=null){
                System.out.println("主线程组："+thread.getName());
            }
        }


    }
}
