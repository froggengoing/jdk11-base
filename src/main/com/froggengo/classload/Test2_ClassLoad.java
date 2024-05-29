package com.froggengo.classload;

/**
 * @author fly
 * @create 2024-05-29-14:19
 **/
public class Test2_ClassLoad {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader sysLoader = ClassLoader.getSystemClassLoader();
        ClassLoader platformLoader = ClassLoader.getPlatformClassLoader();
        ClassLoader classLoader = Test1_ClassLoad.class.getClassLoader();
        Class<?> aClass = classLoader.loadClass("com.froggengo.classload.ChildrenClass");
        System.out.println(aClass);
    }
}
