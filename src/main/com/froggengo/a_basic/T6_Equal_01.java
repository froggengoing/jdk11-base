package com.froggengo.a_basic;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author fly
 * @create 2024-06-12-0:59
 **/
public class T6_Equal_01 {

    @Test
    public void test12() {
        int[][] arr = {{1, 2, 3}, {1, 2, 3}};
        int[][] arr2 = arr.clone();
        arr2[0][0] = 10;
        // arr的值呗改变了
        // arr的数组是直接比较指针
        //false，直接比较指针
        System.out.println(arr2.equals(arr));
        // true ,比较每个元素
        System.out.println(Arrays.equals(arr, arr2));
    }
}
