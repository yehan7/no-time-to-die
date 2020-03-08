package com.yh.demo.juc.aync;

/**
 * @Description: synchronized生产消费者
 * @Since: YH007
 * @Date: 2020/3/6
 */
public class CakeProducerConsumerDemo
{


    public static void main(String[] args)
    {

        Cake cake = new Cake();
        for (int i = 0; i < 10; i++)
        {
            new Thread(() -> {
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
            new Thread(() -> {
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
            new Thread(() -> {
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
            new Thread(() -> {
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

class Cake
{

    private int num = 0;


    public synchronized void increment() throws InterruptedException
    {

        while (num != 0)
        {
            this.wait();
        }
        num++;

        System.out.println(Thread.currentThread().getName() + " " + num);

        this.notifyAll();

    }

    public synchronized void decrement() throws InterruptedException
    {

        while (num == 0)
        {
            this.wait();
        }
        num--;

        System.out.println(Thread.currentThread().getName() + " " + num);

        this.notifyAll();
    }

}
