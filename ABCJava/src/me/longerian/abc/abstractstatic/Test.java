package me.longerian.abc.abstractstatic;

import java.util.Map;

/**
 * Created by huifeng.hxl on 2014/10/29.
 */
public class Test {

    public static void main(String[] args) {
        ConcreteA a = new ConcreteA();
        ConcreteB b = new ConcreteB();

        Map<String, String> amap = a.getMap();
        Map<String, String> bmap = b.getMap();

        System.out.println("amap = " + amap);
        System.out.println("bmap = " + bmap);
    }

}
