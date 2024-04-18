package com.froggengo.jvm;

/**
 * 深入理解Java虚拟机：JVM高级特性与最佳实践（第3版） 代码清单3-1
 *
 * @author fly
 * @create 2024-04-18-14:49
 **/
public class GC1_ReferenceCountingGC {
    private static final int _1MB = 1024 * 1024;
    public Object instance = null;
    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在GC日志中看清楚是否有回收过
     */
    private byte[] bigSize = new byte[2 * _1MB];

    /**
     * -XX:+PrintGCDetails
     */
    public static void main(String[] args) {
        GC1_ReferenceCountingGC objA = new GC1_ReferenceCountingGC();
        GC1_ReferenceCountingGC objB = new GC1_ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        // 假设在这行发生GC，objA和objB是否能被回收？
        System.gc();
        System.out.println(objB.instance.hashCode());
        objB = null;

        System.gc();
    }
}

