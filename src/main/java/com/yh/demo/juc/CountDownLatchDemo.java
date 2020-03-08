package com.yh.demo.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @Description: 倒计时锁门
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class CountDownLatchDemo
{

    public static void main(String[] args)
    {

        CountDownLatch countDownLatch = new CountDownLatch(10);
        closeDoor(countDownLatch);

    }


    public static void closeDoor(CountDownLatch countDownLatch)
    {
        for (int i = 0; i < 10; i++)
        {
            new Thread(() ->
            {
                System.out.println(Thread.currentThread().getName() + "走了");
                countDownLatch.countDown();
            }).start();
        }

        try
        {
            countDownLatch.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "关门");

    }
}




