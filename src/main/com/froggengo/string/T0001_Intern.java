package com.froggengo.string;

/**
 * @author fly
 * @create 2024-04-29-9:31
 **/
public class T0001_Intern {
    public static void main(String[] args) {
        String s = new String("1234");
        String s1 = new String("1234");
        //System.out.println(s == s1);//false
        System.out.println(s.intern() == s1.intern());//true


        String hello = "Hello", lo = "lo";
        System.out.print((hello == "Hello") + " ");//true
        System.out.print((Other.hello == hello) + " ");//true
        System.out.print((hello == ("Hel"+"lo")) + " ");//true
        System.out.print((hello == ("Hel"+lo)) + " ");//false
        System.out.println(hello == ("Hel"+lo).intern());//true
    }

}
class Other { static String hello = "Hello"; }
