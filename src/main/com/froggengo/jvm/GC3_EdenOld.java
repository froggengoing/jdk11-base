package com.froggengo.jvm;

/**
 * @author fly
 * @create 2024-04-18-16:56
 **/
public class GC3_EdenOld {
    private static final int _1MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     * -Xmn：指定JVM年轻代的内存大小，默认值是JVM堆内存的1/3。
     * -XX：Survivor-Ratio=8决定了新生代中Eden区与一个Survivor区的空间比例是8∶1
     */
    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }
}
