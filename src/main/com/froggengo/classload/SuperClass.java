package com.froggengo.classload;

public class SuperClass {
    static {
        System.out.println("super静态块");
    }

    private String helloabc = "abc";

    public SuperClass(String helloabc) {
        this.helloabc = helloabc;
        System.out.println("supper有参");
    }

    public SuperClass() {
        System.out.println("supper无参");
    }

    public String getHelloabc() {
        return helloabc;
    }

    public void setHelloabc(String helloabc) {
        this.helloabc = helloabc;
    }


} 