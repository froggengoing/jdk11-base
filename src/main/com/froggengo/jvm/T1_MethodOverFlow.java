package com.froggengo.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author fly
 * @create 2024-04-18-11:52
 **/
public class T1_MethodOverFlow {
    /**
     * -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:+HeapDumpOnOutOfMemoryError
     * @param args
     */
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(TestClass.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o, objects);
                }
            });
            enhancer.create();
        }

    }

    static class TestClass {

    }
}
