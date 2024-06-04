package com.froggengo.a_basic;

/**
 * @author fly
 * @create 2024-06-04-12:46
 **/
public class T4_Imp1 implements T4_C1{
    //@Override
    //public int add(Integer value) {
    //    return value;
    //}

    public static void main(String[] args) {
        T4_Imp1 imp1 = new T4_Imp1();
        System.out.println(imp1.add(1));
    }

    @Override
    public int add(Integer value) {
        return 0;
    }

    @Override
    public int add1(Integer value) {
        return T4_C1.super.add1(value);
    }

}
