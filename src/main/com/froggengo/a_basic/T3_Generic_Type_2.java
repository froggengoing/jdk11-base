package com.froggengo.a_basic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fly
 * @create 2024-05-29-20:19
 **/
public class T3_Generic_Type_2 {
    public static void main(String[] args) {
        ArrayList<Number> list = new ArrayList<>();
        addNum(list);
        for (Number number : list) {
            System.out.println(number);
        }
        System.out.println("============");
        ArrayList<Object> lob = new ArrayList<>();
        addNum(lob);
        for (Object number : lob) {
            System.out.println(number);
        }
    }

    public static void addNum(List<? super Number> list) {
        double sum = 0;
        // 这里无法读取，因为只能确定集合的下界是Number
        //也有可能是Object
        //for (Number number : list) {
        //    sum = sum + number.doubleValue();
        //}
        list.add(1.0);
        list.add(1);
    }
}

class T3_Parent {

    public void parent() {
        System.out.println("parent");
    }
}

class T3_Child extends T3_Parent {

    public void child() {
        System.out.println("child");
    }
}

class T3_Generic<T extends T3_Parent> {
    private T p;

    public T getP() {return p;}

    public T3_Generic setP(T t) {
        this.p = t;
        return this;
    }
}