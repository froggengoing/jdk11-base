package com.froggengo.a_basic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fly
 * @create 2024-05-29-19:21
 **/
public class T3_Generic_Type_1 {
    public static void main(String[] args) {
        //? extends Number确定集合里面都是Number或子类，
        //  由于不知道是什么具体子类，所以不能做设置操作
        //    比如
        List<? extends Number> list = new ArrayList<>();
        //这里编译错误
        //list.add(Integer.valueOf(1));

        List<Integer> in = List.of(1, 2, 3);
        List<Double> dn = List.of(1d, 2d, 3d);
        print(in);
        print(dn);

        //这里编译报错，因为只能接受List<Number>
        //printNumber(in);
        //printNumber(dn);
        List<Number> nn = List.of(1, 2, 3);
        printNumber(nn);
    }

    public static void print(List<? extends Number> list) {
        double sum = 0;
        for (Number number : list) {
            sum = sum + number.doubleValue();
        }
        System.out.println(sum);
    }

    public static void printNumber(List<Number> list) {
        double sum = 0;
        for (Number number : list) {
            sum = sum + number.doubleValue();
        }
        System.out.println(sum);
    }
}

