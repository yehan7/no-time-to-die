package com.yh.demo.juc.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Lock生产者消费者
 * @Since: YH007
 * @Date: 2020/3/6
 */
public class CakeProducerConsumerLockDemo
{


    public static void main(String[] args)
    {

        Cakes cake = new Cakes();
        for (int i = 0; i < 10; i++)
        {
            new Thread(() ->
            {
                try
                {
                    cake.increment();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }, "A").start();
        }
        for (int i = 0; i < 10; i++)
        {
            new Thread(() ->
            {
                try
                {
                    cake.decrement();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }, "B").start();
        }

        for (int i = 0; i < 10; i++)
        {
            new Thread(() ->
            {
                try
                {
                    cake.increment();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }, "C").start();
        }
        for (int i = 0; i < 10; i++)
        {
            new Thread(() ->
            {
                try
                {
                    cake.decrement();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }, "D").start();
        }
    }
}

class Cakes
{

    private int num = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();


    public void increment() throws InterruptedException
    {
        lock.lock();

        try
        {
            while (num != 0)
            {
                condition.await();
            }
            num++;

            System.out.println(Thread.currentThread().getName() + " " + num);

            condition.signalAll();
        }
        finally
        {
            lock.unlock();

        }

    }

    public void decrement() throws InterruptedException
    {

        lock.lock();
        try
        {
            while (num == 0)
            {
                condition.await();
            }
            num--;

            System.out.println(Thread.currentThread().getName() + " " + num);

            condition.signalAll();
        }
        finally
        {

            lock.unlock();
        }
    }

}
