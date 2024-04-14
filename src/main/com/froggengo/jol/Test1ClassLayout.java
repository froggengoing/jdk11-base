package com.froggengo.jol;

import org.junit.jupiter.api.Test;
import org.openjdk.jol.datamodel.X86_32_DataModel;
import org.openjdk.jol.datamodel.X86_64_COOPS_DataModel;
import org.openjdk.jol.datamodel.X86_64_DataModel;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.layouters.CurrentLayouter;
import org.openjdk.jol.layouters.HotSpotLayouter;
import org.openjdk.jol.layouters.Layouter;

public class Test1ClassLayout {

    @Test
    public void testEmpty() {

        Layouter l;

        /*------------当前虚拟机----------------------*/

        l = new CurrentLayouter();

        System.out.println("**当前虚拟机***" + l);

        System.out.println(ClassLayout.parseClass(Class1Empty.class, l).toPrintable());

    }

    @Test
    public void test14() {

        Layouter l;

        /*------------当前虚拟机----------------------*/

        l = new CurrentLayouter();

        System.out.println("**当前虚拟机***" + l);

        System.out.println(ClassLayout.parseClass(Class2ObjectSize.class, l).toPrintable());

        /*--------------模拟32位虚拟机--------------------*/

        l = new HotSpotLayouter(new X86_32_DataModel());

        System.out.println("**模拟32位虚拟机***" + l);

        System.out.println(ClassLayout.parseClass(Class2ObjectSize.class, l).toPrintable());

        /*--------------模拟64位虚拟机--------------------*/

        l = new HotSpotLayouter(new X86_64_DataModel());

        System.out.println("***模拟64位虚拟机**" + l);

        System.out.println(ClassLayout.parseClass(Class2ObjectSize.class, l).toPrintable());

        /*--------------模拟64位虚拟机，使用oop-klassmodel--------------------*/

        l = new HotSpotLayouter(new X86_64_COOPS_DataModel());

        System.out.println("**模拟64位虚拟机，使用oop-klassmodel***" + l);

        System.out.println(ClassLayout.parseClass(Class2ObjectSize.class, l).toPrintable());
    }


}