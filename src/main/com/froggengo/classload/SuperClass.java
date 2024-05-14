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
    public int SuperClass(){
        System.out.println("普通方法");
        return 0;
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