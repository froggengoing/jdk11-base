package com.froggengo.thread;

/**
 * @author fly
 * @create 2024-04-08-13:13
 **/
public class T3_Test {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    public static void main(String[] args) {
        System.out.println(COUNT_BITS);
        System.out.println(COUNT_MASK);
        System.out.println(RUNNING);
        System.out.println(Integer.toBinaryString(1));
        System.out.println(Integer.toBinaryString(-0));
//        原码：00000000000000000000000000000001
//        反码：11111111111111111111111111111110
//        补码：11111111111111111111111111111111
        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(-2));
        System.out.println(Integer.toBinaryString(-5));
    }
}
