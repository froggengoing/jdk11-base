package com.froggengo.jvm;

import java.util.ArrayList;

/**
 * @author fly
 * @create 2024-04-18-14:03
 **/
public class T3_HeadOutOfMemory {
    /**
     * -Xmx500m
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        ArrayList<EmptyClass> list = new ArrayList<>(10240000);
        while (true) {
            list.add(new EmptyClass());
        }
    }

    static class EmptyClass {

    }
}
