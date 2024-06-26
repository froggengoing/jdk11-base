package com.froggengo.collection;

import org.junit.jupiter.api.Test;

import java.util.*;
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
     * HashSet 基于 HashMap，所以add和remove相当于操作HashMap的key
     */
    @Test
    public void test29() {
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(1);
        set.remove(1);
        System.out.println(set.contains(1));
    }


    @Test
    public void test43() {
        HashSet<String> set = new HashSet<>();
        set.add("abc");
        set.add("bcd");
        set.add("ccc");
        // HashSet打印顺序并不等于输入顺序
        for (String s : set) {
            System.out.println(s);
        }
        System.out.println("==========");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("abc");
        linkedHashSet.add("bcd");
        linkedHashSet.add("ccc");
        // HashSet打印顺序并不等于输入顺序
        for (String s : linkedHashSet) {
            System.out.println(s);
        }
    }

    @Test
    public void test60() {
        HashMap<String, String> map = new HashMap<>();
        map.put("abc", "abc");
        map.put("bcd", "bcd");
        map.put("ccc", "ccc");
        // HashSet打印顺序并不等于输入顺序
        for (Map.Entry<String, String> s : map.entrySet()) {
            System.out.println(s.getKey());
        }
        System.out.println("==========");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("abc", "abc");
        linkedHashMap.put("bcd", "bcd");
        linkedHashMap.put("ccc", "ccc");
        // HashSet打印顺序并不等于输入顺序
        for (Map.Entry<String, String> s : linkedHashMap.entrySet()) {
            System.out.println(s.getKey());
        }
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
