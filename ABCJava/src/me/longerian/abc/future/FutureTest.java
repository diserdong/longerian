package me.longerian.abc.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by huifeng.hxl on 2015/1/17.
 */
public class FutureTest {

    public static class Task implements Callable<String> {
        @Override
        public String call() throws Exception {
            String tid = String.valueOf(Thread.currentThread().getId());
            System.out.printf("Thread#%s : in call\n", tid);
            return tid;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int ceil = (int) Math.ceil(5.0f / 3);
        System.out.println("ceil = " + ceil);

        List<Future<String>> results = new ArrayList<Future<String>>();
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0; i<100;i++)
            results.add(es.submit(new Task()));

        System.out.printf("Thread#%s : in main\n", (Thread.currentThread().getId()));
        for(Future<String> res : results)
            System.out.println(res.get());
    }

}
