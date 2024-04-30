package com.froggengo.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
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
