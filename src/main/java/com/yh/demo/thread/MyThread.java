package com.yh.demo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class MyThread implements Callable {
    @Override
    public Object call() throws Exception {

        TimeUnit.SECONDS.sleep(4);
        System.out.println("执行call方法");
        return 1024;
    }
}


class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(new MyThread());
        new Thread(futureTask).start();


        System.out.println(futureTask.get());
    }


}