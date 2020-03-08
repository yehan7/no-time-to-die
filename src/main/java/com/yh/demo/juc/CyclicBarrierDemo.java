package com.yh.demo.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @Description: 累加收集龙珠
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class CyclicBarrierDemo
{
    public static void main(String[] args)
    {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () ->
        {
            System.out.println("召唤神龙");
        });

        cellet(cyclicBarrier);
    }


    public static void cellet(CyclicBarrier cyclicBarrier)
    {
        for (int i = 1; i <= 7; i++)
        {
            final int num = i;
            new Thread(() ->
            {
                System.out.println(Thread.currentThread().getName() + "收集到第" + num + "颗龙珠");
                try
                {
                    cyclicBarrier.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                catch (BrokenBarrierException e)
                {
                    e.printStackTrace();
                }

            }).start();
        }


    }
}
