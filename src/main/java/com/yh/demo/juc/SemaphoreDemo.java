package com.yh.demo.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 抢车位
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class SemaphoreDemo
{

    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 8; i++)
        {
            new Thread(() -> {

                try
                {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到了车位");
                    TimeUnit.SECONDS.sleep(3);

                    System.out.println(Thread.currentThread().getName() + "离开了车位");

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    semaphore.release();
                }


            }, "车主" + i).start();
        }
    }
}
