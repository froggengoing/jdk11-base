package com.froggengo.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.datamodel.X86_64_DataModel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.vm.VM;

/**
 * @author fly
 * @create 2024-04-13-14:40
 **/
public class Test2ObjectHeader {

    /**
     * -XX:-UseBiasedLocking
     */
    @Test
    public void test9(){
        System.out.println(VM.current().details());

        //问题1：Thread.sleep(6000);

        Class1Empty classHeaderInit = new Class1Empty();

        //问题二：System.out.println(classHeaderInit.hashCode());

        HotSpotLayouter hotSpotLayouter = new HotSpotLayouter(new X86_64_DataModel());

        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
    }

    @Test
    public void test30() throws InterruptedException {
        System.out.println(VM.current().details());

        //问题1：Thread.sleep(6000);
        Thread.sleep(6000);
        Class1Empty classHeaderInit = new Class1Empty();

        //问题二：System.out.println(classHeaderInit.hashCode());

        HotSpotLayouter hotSpotLayouter = new HotSpotLayouter(new X86_64_DataModel());

        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
    }
    @Test
    public void test44() throws InterruptedException {
        System.out.println(VM.current().details());

        //问题1：Thread.sleep(6000);
        Thread.sleep(6000);
        Class1Empty classHeaderInit = new Class1Empty();

        //问题二：System.out.println(classHeaderInit.hashCode());
        int hashcode = classHeaderInit.hashCode();
        System.out.println(hashcode);
        System.out.println(Integer.toHexString(hashcode));
        System.out.println(Integer.toBinaryString(hashcode));
        HotSpotLayouter hotSpotLayouter = new HotSpotLayouter(new X86_64_DataModel());

        System.out.println(ClassLayout.parseInstance(classHeaderInit,hotSpotLayouter).toPrintable());
    }


}
