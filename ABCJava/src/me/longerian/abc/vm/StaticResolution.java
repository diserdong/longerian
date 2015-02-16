package me.longerian.abc.vm;

/**
 * Created by huifeng.hxl on 2015/2/16.
 */
public class StaticResolution {

    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }

}
