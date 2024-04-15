package com.froggengo.design;

import java.util.Random;

/**
 * @author fly
 * @create 2024-04-15-11:56
 **/
public class D1_SingleOb {

    /**
     * 注意这里必须使用volatile
     */
    private volatile static D1_SingleOb instance = null;

    private D1_SingleOb() {
    }

    public static D1_SingleOb getInstance() {
        if (instance == null) {

            synchronized (D1_SingleOb.class) {
                if (instance == null) {
                    instance = new D1_SingleOb();
                }
            }
        }
        return instance;
    }

    public void print() {
        System.out.println(new Random().nextInt(100));
    }
}

