package com.froggengo.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.datamodel.X86_64_DataModel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.vm.VM;

/**
 * @author fly
 * @create 2024-04-13-15:08
 **/
public class Test2ObjectHeaderLock {
    @Test
    public void test9() throws InterruptedException {
        System.out.println(VM.current().details());

//        Class1Empty classHeaderInit1 = new Class1Empty();
//        System.out.println(classHeaderInit1.hashCode());
        Thread.sleep(5000);

        System.out.println(Thread.currentThread().getId());
        System.out.println(Long.toBinaryString(Thread.currentThread().getId()));
        Class1Empty classHeaderInit = new Class1Empty();
        HotSpotLayouter hotSpotLayouter = new HotSpotLayouter(new X86_64_DataModel());
//       1. 特殊的偏向锁状态
        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
        synchronized (classHeaderInit){
//            2. 偏向锁状态，记录当前线程id
            System.out.println("=====inside synchronized Begin===========");
            System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
            System.out.println("=====inside synchronized End===========");
        }
        Thread.sleep(2000);
//        此时保留偏向锁状态
        System.out.println("=====outside synchronized Begin===========");
        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
        System.out.println("=====outside synchronized End===========");
//        因为偏向锁存储线程id的位置与存储hashcode的位置相同，所以这里会导致退出偏向锁
        System.out.println(classHeaderInit.hashCode());
        System.out.println(Integer.toHexString(classHeaderInit.hashCode()));
        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
    }

    /**
     * 在synchronized中hashcode，导致偏向锁升级位重量级锁
     * @throws InterruptedException
     */
    @Test
    public void test47() throws InterruptedException {
        System.out.println(VM.current().details());


        Thread.sleep(5000);

        System.out.println(Thread.currentThread().getId());
        System.out.println(Long.toBinaryString(Thread.currentThread().getId()));
        Class1Empty classHeaderInit = new Class1Empty();
        HotSpotLayouter hotSpotLayouter = new HotSpotLayouter(new X86_64_DataModel());
//       1. 特殊的偏向锁状态
        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
        synchronized (classHeaderInit){
//            2. 偏向锁状态，记录当前线程id
            System.out.println("=====inside synchronized Begin===========");
            System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
            //        因为偏向锁存储线程id的位置与存储hashcode的位置相同，所以这里会导致退出偏向锁
            System.out.println("=====inside synchronized and get hashcode===========");
            System.out.println(classHeaderInit.hashCode());
            System.out.println(Integer.toHexString(classHeaderInit.hashCode()));
            System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
            System.out.println("=====inside synchronized End===========");

        }
    }
}
