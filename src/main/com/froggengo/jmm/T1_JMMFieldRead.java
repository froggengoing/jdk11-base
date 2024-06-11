package com.froggengo.jmm;

/**
 * @author fly
 * @create 2024-06-11-10:36
 **/
public class T1_JMMFieldRead {

    /**
     * volatile:保证了可见性
     */
    //static volatile boolean flag = false;
    /**
     * 共享变量，在线程存在不可见性，因为在线程中间会保持副本，
     * 所以即使线程2修改了flag值，线程一也无法感知，所以线程一无法一直无法退出
     */
    static boolean flag = false;


    public static void main(String[] args) throws InterruptedException {


        new Thread(() -> {
            System.out.println("thread-1 start");
            while (!flag) { //  读取变量

            }
            System.out.println("thread-1 end");
        }).start();
        // 这里必须先睡眠
        Thread.sleep(100);
        new Thread(() -> {
            System.out.println("thread-2 start");
            flag = true;
            System.out.println("thread-2 end");
        }).start();
    }

}
