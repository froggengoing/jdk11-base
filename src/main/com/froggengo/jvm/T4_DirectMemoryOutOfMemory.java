package com.froggengo.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author fly
 * @create 2024-04-18-14:29
 **/
public class T4_DirectMemoryOutOfMemory {
    public static final int _size = 1 * 1024 * 1024;

    /**
     * -Xmx20M -XX:MaxDirectMemorySize=10M
     * 由直接内存导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见有什么明显的异常
     * 情况，如果读者发现内存溢出之后产生的Dump文件很小，而程序中又直接或间接使用了
     * DirectMemory（典型的间接使用就是NIO），那就可以考虑重点检查一下直接内存方面的原因了。
     * @param args
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IllegalAccessException {
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
        while (true){
            unsafe.allocateMemory(_size);
        }
    }
}
