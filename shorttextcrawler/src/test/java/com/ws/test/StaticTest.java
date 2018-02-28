package com.ws.test;

public class StaticTest {

    public static int num=5;

    static{
        System.out.println(num);
    }

    public StaticTest(){
        System.out.println(num);
    }

    public static void main(String[] args){
        StaticTest test=new StaticTest();
        A.print();
    }
}

class A{
    public static String str;

    static{
        str=new String("hello");
    }

    public static void print(){
        System.out.println(str);
    }
}
