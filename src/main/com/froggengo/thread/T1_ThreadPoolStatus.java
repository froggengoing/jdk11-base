package com.froggengo.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-04-08-13:13
 **/
public class T1_ThreadPoolStatus {


    /**
     * @see ThreadPoolExecutor#ctl
     * 线程池使用一个变量，保存线程数量以及线程状态
     * 线程数理论最大值：(2^29)-1
     * 线程状态保存再，29、30、31、32位置
     */
//    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;

    /**
     * @see ThreadPoolExecutor#RUNNING
     */
    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    @Test
    public void test33(){
        System.out.println(COUNT_BITS);
        System.out.println(COUNT_MASK);
        System.out.println(RUNNING);
        System.out.println(SHUTDOWN);
        System.out.println(STOP);
        System.out.println(TIDYING);
        System.out.println(TERMINATED);
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(-0));
//        原码：00000000000000000000000000000001
//        反码：11111111111111111111111111111110
//        补码：11111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-5));
    }

    @Test
    public void test54(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                10,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<Runnable>(100));
        System.out.println(executor.toString());
    }

}
