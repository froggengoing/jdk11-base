package com.froggengo.interview;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author fly
 * @create 2024-04-16-15:56
 **/
public class T2_Comparator {


    /**
     * https://javaguide.cn/java/collection/java-collection-questions-01.html#comparable-%E5%92%8C-comparator-%E7%9A%84%E5%8C%BA%E5%88%AB
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(1, "1"));
        persons.add(new Person(20, "20"));
        persons.add(new Person(12, "12"));
        persons.add(new Person(14, "14"));
        persons.add(new Person(13, "113"));

        System.out.println("排序前");
        for (Person person : persons) {
            System.out.println(person);
        }
        Collections.<Person>sort(persons, (l, r) -> {
            return l.age - r.age;
        });
        System.out.println("排序后");
        for (Person person : persons) {
            System.out.println(person);
        }

    }
}

class Person {
    int age;
    String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}

