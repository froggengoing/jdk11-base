package com.froggengo.thread.thread;

/**
 * @author fly
 * @create 2024-04-15-16:38
 **/
public class T12_ThreadException {
    /**
     * 设置unchecked异常的处理器。
     *
     * @param args
     * @see ThreadGroup#uncaughtException(java.lang.Thread, java.lang.Throwable)
     */
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(e.getMessage());
            System.out.println(Thread.currentThread().getName() + "线程抛出异常。");
        });
        new Thread(() -> {
            System.out.println("线程正在工作：" + Thread.currentThread().getName());
            System.out.println(1 / 0);
        }, "t1").start();
    }
}
