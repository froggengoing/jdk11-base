package com.froggengo.classload;


import java.security.CodeSource;
import java.sql.Driver;
import java.util.logging.Logger;

/**
 * @author fly
 * @create 2024-05-29-14:35
 **/
public class Test3_ClassLoad {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(Test3_ClassLoad.class.getClassLoader());
        System.out.println(Test3_ClassLoad.class.getClassLoader().getParent());
        System.out.println(Test3_ClassLoad.class.getClassLoader().getParent().getParent());
        //System.out.println(Test3_ClassLoad.class.getClassLoader().getParent().getParent().getParent());
        /**
         * jdk.internal.loader.ClassLoaders$AppClassLoader@71bc1ae4
         * jdk.internal.loader.ClassLoaders$PlatformClassLoader@629f0666
         * null
         * java.lang.NullPointerException
         */
        /**
         * 1. BootstrapClassLoader是由JVM本身实现的，不是Java代码的一部分，因此在Java层面上无法直接访问它。所以这里为空
         */
        System.out.println(java.util.List.class.getClassLoader() == null);
        System.out.println(java.io.File.class.getClassLoader() == null);
        Class<?> clazz = Class.forName("jdk.internal.reflect.Reflection");
        System.out.println(clazz.getClassLoader() == null);
        System.out.println(Logger.class.getClassLoader() == null);
        System.out.println(java.sql.Driver.class.getClassLoader() == ClassLoader.getPlatformClassLoader());
        /**
         * PlatformClassLoader类加载路径：jrt:/java.sql
         */
        CodeSource codeSource = Driver.class.getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            System.out.println("Driver路径:" + codeSource.getLocation());
        }
    }
}
