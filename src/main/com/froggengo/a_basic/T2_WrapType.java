package com.froggengo.a_basic;

/**
 * @author fly
 * @create 2024-05-29-18:13
 **/
public class T2_WrapType {
    public static void main(String[] args) {
        /**
         *     bipush 100
         *     invokestatic 'java/lang/Integer.valueOf','(I)Ljava/lang/Integer;'
         */
        Integer i1 = 200;
        /**
         *     invokevirtual 'java/lang/Integer.intValue','()I'
         */
        int i2 = Integer.valueOf(200);
        /**
         * 这里做比较也会发生拆箱
         */
        System.out.println(i1 == i2 == true);

        Integer i3 = Integer.valueOf(200);
        System.out.println(i1 == i3 == false);

        /**
         * 包装类缓存机制
         */
        Integer i4 = Integer.valueOf(127);
        Integer i5 = Integer.valueOf(127);
        System.out.println(i4 == i5 == true);

        Long i6 = Long.valueOf(-128);
        Long i7 = Long.valueOf(-128);
        System.out.println(i6 == i7 == true);

    }
}
