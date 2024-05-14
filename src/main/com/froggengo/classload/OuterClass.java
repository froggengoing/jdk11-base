package com.froggengo.classload;

/**
 * @author fly
 * @create 2024-05-13-18:03
 **/
public class OuterClass {


    public void print() {

        System.out.println("OuterClass");
        new InnerClass() {
            public void print() {
                System.out.println("AnnoClass");
            }
        }.print();
    }

    class InnerClass {
        public void print() {
            class PartnerClass {
                public void print() {
                    System.out.println("PartnerClass");
                }
            }
            System.out.println("InnerClass");
            new PartnerClass().print();
        }
    }
    static class InnerStaticClass {
        public void print() {
            System.out.println("InnerStaticClass");
        }
    }
    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        outerClass.print();
        InnerClass innerClass = outerClass.new InnerClass();
        innerClass.print();
        OuterClass.InnerStaticClass innerStaticClass = new OuterClass.InnerStaticClass();
        innerStaticClass.print();
    }
}
