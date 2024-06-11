package com.froggengo.a_basic;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @author fly
 * @create 2024-06-11-23:48
 * https://blog.csdn.net/zhangzehai2234/article/details/134300926
 **/
public class T5_DeepCopyExample {
    public static void main(String[] args) {
        // 创建一个包含嵌套对象的对象
        Person person = new Person("John", 25, new Address("American", "New York", "NY"));
        // 创建一个深拷贝对象
        Person deepCopy = T5_DeepCopyExample.deepCopyUsingClone(person);
        // 修改原始对象的属性，并查看深拷贝对象的属性是否也发生了改变
        person.setName("Jane");
        person.setAge(30);
        person.getAddress().setStreet("456 Elm St");
        System.out.println("Original Person:");
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        System.out.println("Address:");
        System.out.println(person.getAddress().getStreet());
        System.out.println("Deep Copy Person:");
        System.out.println("Name: " + deepCopy.getName());
        System.out.println("Age: " + deepCopy.getAge());
        System.out.println("Address:");
        System.out.println(deepCopy.getAddress().getStreet());
    }

    public static <T> T deepCopyUsingClone(T object) {
        try {
            T copy = (T) object.getClass().newInstance();
            copy = (T) object.getClass().getMethod("clone").invoke(object);
            return copy;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Person implements Cloneable, Serializable {
    private String name;
    private int age;
    private Address address;

    public Person() {
    }

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // 省略了构造方法和Getter和Setter方法
    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {return name;}

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {return age;}

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public Address getAddress() {return address;}

    public Person setAddress(Address address) {
        this.address = address;
        return this;
    }
}


class PersonForDeepClone extends Person {
    @Override
    public Person clone() {
        return (Person) super.clone();
    }

}

class Address implements Cloneable, Serializable {
    private String street;
    private String city;
    private String state;
    // Constructor, getters, and setters omitted for brevity

    public Address() {
    }

    public Address(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    @Override
    public Address clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getStreet() {return street;}

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {return city;}

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {return state;}

    public Address setState(String state) {
        this.state = state;
        return this;
    }
}
