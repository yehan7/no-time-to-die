package com.yh.demo.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: Lock顺序的生产消费者
 * @Since: YH007
 * @Date: 2020/3/6
 */
public class CirclePrintLockDemo
{

    public static void main(String[] args)
    {
        Circle circle = new Circle();

        for (int i = 0; i < 10; i++)
        {
            new Thread(() -> {
                try
                {
                    circle.print1();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }, "A").start();
        }

        for (int i = 0; i < 10; i++)
        {
            new Thread(() -> {
                try
                {
                    circle.print2();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }, "B").start();
        }

        for (int i = 0; i < 10; i++)
        {
            new Thread(() -> {
                try
                {
                    circle.print3();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }, "C").start();
        }
    }
}


class Circle
{

    private int num = 1; //1和condition1对应，2和condition2对应，3和condition3对应

    private Lock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();


    public void print1()
    {
        lock.lock();
        try
        {
            while (num != 1)
            {
                condition1.await();
            }
            for (int i = 1; i <= 5; i++)
            {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            num = 2;
            condition2.signal();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void print2()
    {
        lock.lock();
        try
        {
            while (num != 2)
            {
                condition2.await();
            }
            for (int i = 1; i <= 10; i++)
            {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            num = 3;
            condition3.signal();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void print3()
    {
        lock.lock();
        try
        {
            while (num != 3)
            {
                condition3.await();
            }
            for (int i = 1; i <= 15; i++)
            {
                System.out.println(Thread.currentThread().getName() + "打印" + i + "次");
            }
            num = 1;
            condition1.signal();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }
}
