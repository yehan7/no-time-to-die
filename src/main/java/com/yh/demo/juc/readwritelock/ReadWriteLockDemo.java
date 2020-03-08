package com.yh.demo.juc.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description: 读写锁ReadWriteLock
 * @Since: YH007
 * @Date: 2020/3/7
 */
public class ReadWriteLockDemo
{
    public static void main(String[] args)
    {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++)
        {
            final int num = i;
            new Thread(() ->
            {
                myCache.put("key", String.valueOf(num));
            }, "写线程" + i).start();
        }

        for (int i = 1; i <= 5; i++)
        {
            final int num = i;
            new Thread(() ->
            {
                myCache.get("key");
            }, "读线程" + i).start();
        }
    }
}


class MyCache
{

    private volatile Map<String, Object> map = new HashMap();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key, String value)
    {
        lock.writeLock().lock();

        try
        {
            System.out.println(Thread.currentThread().getName() + "写入数据" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入数据完成");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

            lock.writeLock().unlock();
        }

    }

    public void get(String key)
    {
        lock.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName() + "读取数据" + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取数据完成，值为" + o.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.readLock().unlock();
        }
    }
}
