package com.froggengo.interview;

/**
 * https://blog.csdn.net/buzhidao2333shuosha/article/details/132842024
 * 该实现会导致线程一直处于wait状态
 */
public class PrintNumber{
    private int num;
    private static final Object lock = new Object();
    public void print(int order){
        while(true){
            synchronized(lock){
                if(num >= 10){break;}
                while(num % 3 == order){
                    try{
                        System.out.println(Thread.currentThread().getName()+num);
                        num++;
                        lock.notifyAll();
                        lock.wait();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[]args){
        PrintNumber test = new PrintNumber();
        Thread t0 = new Thread(()->{test.print(0);},"A");
        Thread t1 = new Thread(()->{test.print(1);},"B");
        Thread t2 = new Thread(()->{test.print(2);},"C");

        t0.start();
        t1.start();
        t2.start();

    }
}
