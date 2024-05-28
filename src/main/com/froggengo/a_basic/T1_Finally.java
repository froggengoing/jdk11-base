package com.froggengo.a_basic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fly
 * @create 2024-05-29-6:36
 **/
public class T1_Finally {
    public static void main(String[] args) {
        System.out.println(cal() == 15);
        System.out.println(cal1() == 10);
        System.out.println(cal1_1().get() == 15);
        System.out.println(cal2_1().get() == 15);
    }

    public static int cal() {
        int a = 5;
        try {
            a += 5;

        } finally {
            // 先执行
            a += 5;
        }
        //先返回
        return a;
    }

    public static int cal1() {
        int a = 5;
        try {
            a += 5;
            // 先返回
            return a;
        } finally {

            a += 5;
        }
    }

    public static AtomicInteger cal1_1() {
        AtomicInteger atom = new AtomicInteger(5);
        try {
            atom.addAndGet(5);
        } finally {
            atom.addAndGet(5);
        }
        return atom;
    }

    public static AtomicInteger cal2_1() {
        AtomicInteger atom = new AtomicInteger(5);
        try {
            atom.addAndGet(5);
            // 这里先返回引用
            return atom;
        } finally {
            atom.addAndGet(5);
        }

    }
}
