package com.froggengo.jvm;

/**
 * 深入理解Java虚拟机：JVM高级特性与最佳实践（第3版） 代码清单3-2
 * @author fly
 * @create 2024-04-18-15:10
 **/
public class GC2_Finalize {
    private static GC2_Finalize instance = null;

    public static void main(String[] args) throws InterruptedException {
        instance = new GC2_Finalize();

        instance = null;
        System.gc();
        // 因为Finalizer方法优先级很低，暂停0.5秒，以等待它
        Thread.sleep(500);
        if (instance != null) {
            instance.isAlive();
        } else {
            System.out.println("i am dead");
        }

        instance = null;
        System.gc();
        Thread.sleep(500);

        if (instance != null) {
            instance.isAlive();
        } else {
            System.out.println("i am dead");
        }
    }


    public void isAlive() {
        System.out.println("i am still alive");
    }

    /**
     * ‘对对象进行可达性分析，即没有与任何GC-root链关联时，对象会被标记，并筛选，满足以下条件为
     * 1. 没有重载了finalize方法
     * 2. 者finalize()方法已经被虚拟机调用过
     * 文档：Java 虚拟机绝不会对任何给定对象多次调用 finalize 方法。
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize has been execute");
        instance = this;
    }

}
