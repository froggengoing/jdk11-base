package com.froggengo.classload;

public class Test1_ClassLoad {

    public static void main(String[] args) throws InterruptedException {

//第一种 

//        SuperClass nihao = new SuperClass("nihao");
//
//        System.out.println(nihao.getHelloabc());

//      第二种

        Thread.sleep(1000_000);
        ChildrenClass nihao2 = new ChildrenClass("nihao");

        System.out.println(nihao2.getChildname());

    } 

} 