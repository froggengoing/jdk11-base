package com.froggengo.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 1.idea 编译自带-g，借助asm读取字节码的本地变量表，可以读取方法名
 * 2.maven compile插件默认-g，同上
 * 3.-parameters 可以直接反射从方法名中获取方法名
 * 4.接口无法借助asm读取本地变量表，-parameters也可以获取接口的方法名，但接口的参数名大多数无意义，因为子类可以修改
 * https://www.cnblogs.com/yourbatman/p/11532689.html
 * https://blog.csdn.net/qq_33535433/article/details/118106567
 * @author fly
 * @create 2024-04-29-18:24
 **/
public class R0001_Reflect_01 {
    public static void main(String[] args) {
        Method[] declaredMethods = R1_Interface_1.class.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method declaredMethod = declaredMethods[i];
            System.out.println("=====" + declaredMethod + "======");
            Parameter[] parameters = declaredMethod.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName() + "==" + parameter.getType());
            }
        }

    }

}
