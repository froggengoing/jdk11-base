package com.froggengo.jvm;

/**
 * @author fly
 * @create 2024-04-18-11:52
 **/
public class T1_MethodOverFlow_01 {
    /**
     * -XX:MetaspaceSize=100m -XX:MaxMetaspaceSize=100m -XX:+HeapDumpOnOutOfMemoryError
     *
     * @param args
     */
    public static void main(String[] args) {
        long i = 0;
        while (true) {
            i++;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 1000; j++) {
                sb.append(i);
            }
            String s = sb.toString();
            s.intern();
        }

    }

    static class TestClass {

    }
}
