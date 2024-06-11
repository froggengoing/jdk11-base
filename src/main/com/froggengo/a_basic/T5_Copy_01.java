package com.froggengo.a_basic;

import org.junit.jupiter.api.Test;

/**
 * @author fly
 * @create 2024-06-12-0:40
 **/
public class T5_Copy_01 {

    public static void main(String[] args) {
        System.out.println(new CopyOb1().clone());
    }

    /**
     * 数组的浅拷贝
     */
    @Test
    public void test17() {
        int[][] arr = {{1, 2, 3}, {1, 2, 3}};
        int[][] arr2 = arr.clone();
        arr2[0][0] = 10;
        // arr的值呗改变了
        System.out.println(arr[0][0] == 10);
    }
}

//class CopyOb1 /*implements Cloneable*/ {
class CopyOb1 implements Cloneable {
    String name;
    Integer age;

    public CopyOb1() {
    }

    public CopyOb1(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
