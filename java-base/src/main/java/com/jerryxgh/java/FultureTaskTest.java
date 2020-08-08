package com.jerryxgh.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FultureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Long> future = new FutureTask<Long>(new Callable<Long>() {
            public Long call() throws Exception {
                Thread.sleep(10000L);
                return 0L;
            }

        });
        Thread thread = new Thread(future);
        thread.start();
        future.get();
        System.out.println(future.get());
    }
}
