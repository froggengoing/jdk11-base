package com.froggengo.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fly
 * @create 2024-06-11-15:09
 **/
public class T1_Collection {
    @Test
    public void test11() {
        ArrayList<Integer> list = new ArrayList<>();
        // 触发扩容
        list.add(1);
        // 移除对象
        System.out.println(list.remove((Integer) 1));
        list.add(2);
        // 按索引移除
        // 将数组index之后的元素复制到index，最后位置置空
        System.out.println(list.remove(0));
    }

    /**
     * 二义性
     * map.get("test")都为空，两种情况
     * 1. 一种是value为null
     * 2. 一种是没有test这个key
     * 二义性通过containsKey判断
     * 而在
     */
    @Test
    public void test21() {
        Map<String, String> map = new HashMap<>();
        String test = map.get("test");
        System.out.println(test);
        System.out.println("map.containsKey(\"test\"):" + map.containsKey("test"));
        map.put("test", null);
        String test2 = map.get("test");
        System.out.println(test2);
        System.out.println("map.containsKey(\"test\"):" + map.containsKey("test"));

        ConcurrentHashMap<String, String> conMap = new ConcurrentHashMap<>();
        conMap.put("test", null);
    }

    /**
     * hashmap可以put(null,null)值
     */
    @Test
    public void test20() {
        Map<String, String> map = new HashMap<>();
        System.out.println("map.containsKey(null):" + map.containsKey(null));
        // HashMap底层是Node<k,v>[] table
        // 这里的key为null，只是说Node元素的key为null，而hashmap内部已经将key为null换算为0
        String put = map.put(null, "1");
        String s = map.get(null);
        System.out.println("s.equals(\"1\"):" + s.equals("1"));
        map.put(null, "2");
        String s2 = map.get(null);
        System.out.println("s.equals(\"2\"):" + s2.equals("2"));
        System.out.println("map.containsKey(null):" + map.containsKey(null));
    }
}
