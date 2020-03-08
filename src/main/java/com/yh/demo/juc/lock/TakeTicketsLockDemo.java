package com.yh.demo.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Lock抢票
 * @Since: YH007
 * @Date: 2020/3/6
 */
public class TakeTicketsLockDemo
{

    public static void main(String[] args)
    {

        Tickets tickets = new Tickets();

        for (int i = 0; i < 1000; i++)
        {
            new Thread(() ->
            {
                tickets.take();
            }).start();
        }
    }
}


class Tickets
{

    private int num = 100;

    private Lock lock = new ReentrantLock();


    public void take()
    {
        lock.lock();

        try
        {
            while (num == 0)
            {
                System.out.println("没票了");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "抢到了第" + num + "张票！");
            num--;
        }
        finally
        {

            lock.unlock();

        }


    }
}