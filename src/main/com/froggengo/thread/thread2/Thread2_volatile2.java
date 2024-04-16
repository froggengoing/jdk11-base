package com.froggengo.thread.thread2;

public class Thread2_volatile2 {

    private static volatile Thread2_volatile2 instance = null;

    //更好的是双重验证
    public static Thread2_volatile2 getInstance() {
        if (instance == null) {
            instance = new Thread2_volatile2();
        }
        return instance;
    }

    public static void main(String[] args) {
        Thread2_volatile2.getInstance();
    }
}
