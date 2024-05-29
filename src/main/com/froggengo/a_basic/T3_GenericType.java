package com.froggengo.a_basic;

import java.util.Locale;

/**
 * @author fly
 * @create 2024-05-29-18:57
 **/
public class T3_GenericType {
    public static void main(String[] args) {
        Basic<String> integerBasic = new Basic<>("abc");
        System.out.println(integerBasic.id.toUpperCase(Locale.ROOT));
        ChildBasic<Basic<String>, String> basicStringChildBasic = new ChildBasic<>();
        basicStringChildBasic.bs = new Basic<>();
        basicStringChildBasic.bs.id = "12345";
        System.out.println(basicStringChildBasic.bs.id);
    }
}

/**
 * class com/froggengo/a_basic/Basic {
 * <p>
 * <p>
 * // access flags 0x0
 * // signature TT;
 * // declaration: id extends T
 * Ljava/lang/Object; id
 *
 * @param <T>
 * @groovyx.ast.bytecode.Bytecode public void <init>(Object a) {
 * aload 0
 * invokespecial 'java/lang/Object.<init>','()V'
 * aload 0
 * aload 1
 * putfield 'com/froggengo/a_basic/Basic.id','Ljava/lang/Object;'
 * return
 * }
 * }
 */
class Basic<T> {
    T id;

    public Basic() {
    }

    public Basic(T id) {
        this.id = id;
    }
}

/**
 * class com/froggengo/a_basic/ChildBasic {
 *
 *
 *   // access flags 0x0
 *   // signature TR;
 *   // declaration: bs extends R
 *   Lcom/froggengo/a_basic/Basic; bs
 *
 *   @groovyx.ast.bytecode.Bytecode
 *   void <init>() {
 *     aload 0
 *     invokespecial 'java/lang/Object.<init>','()V'
 *     return
 *   }
 * }
 * @param <R>
 * @param <T>
 */
class ChildBasic<R extends Basic<T>, T> {
    R bs;
}