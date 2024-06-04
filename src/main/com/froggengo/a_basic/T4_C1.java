package com.froggengo.a_basic;

/**
 * @author fly
 * @create 2024-06-04-12:43
 **/
interface T4_P1 {

    int add(Integer value);


    default int add1(Integer value) {
        return value + 1;
    }

}

interface T4_P2 {

    int add(Integer value);

    int add1(Integer value);
}
interface T4_P3 {

    default int add0(Integer value) {
        return 0;
    }
}
public interface T4_C1 extends T4_P1, T4_P2 ,T4_P3{


    //int add(Integer value);

    /**
     * com.froggengo.a_basic.T4_C1 inherits abstract and default for add1(Integer)
     * from types com.froggengo.a_basic.T4_P1
     * and com.froggengo.a_basic.T4_P2
     * 编译报错，多个父接口中，同名方法，默认方法，必须在子类中，重写
     */
    @Override
    default int add1(Integer value) {
        return T4_P1.super.add1(value);
    }

}
