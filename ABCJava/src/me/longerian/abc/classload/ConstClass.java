package me.longerian.abc.classload;

/**
 * Created by huifeng.hxl on 2015/2/16.
 */
public class ConstClass {

    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLOWORLD = "hello world";

}
