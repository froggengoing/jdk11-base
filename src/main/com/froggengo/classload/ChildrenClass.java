package com.froggengo.classload;

public class ChildrenClass extends SuperClass {
    static {
        System.out.println("ChildrenClass静态块");
    }

    public String childValue = "你好我是Childeren";
    private String childname;

    ChildrenClass() {
        System.out.println("children 无参");
    }


    ChildrenClass(String childname) {
        this.childname = childname;
        System.out.println("children 有参");
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;

    }

} 