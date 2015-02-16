package me.longerian.abc.classload;

/**
 * Created by huifeng.hxl on 2015/2/16.
 */
public class Test {

    static {
        i = 0; //可以写
//        System.out.println(i); 但不可以读
    }

    static int i = 1;

    static class Parent {
        public static int A = 1;
        static {
            A = 2;
        }
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }

}
