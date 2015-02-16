package me.longerian.abc.vm;

/**
 * Created by huifeng.hxl on 2015/2/16.
 */
public class SlotTest {

    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }

}
