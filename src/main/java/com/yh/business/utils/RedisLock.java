package com.yh.business.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @Description: Redis分布式锁
 * @Since: YH007
 * @Date: 2020/3/14
 */
@Component
public class RedisLock {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 解锁
     *
     * @param key   key
     * @param value value
     **/
    public void unlock(String key, String value) {
        try {
            if (redisTemplate.opsForValue().get(key).equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加锁
     *
     * @param key   key
     * @param value value
     * @return: boolean
     **/
    public boolean lock(String key, String value) {
        //setIfAbsent相当于jedis中的setnx，如果能赋值就返回true，如果已经有值了，就返回false
        //即：在判断这个key是不是第一次进入这个方法
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //第一次，即：这个key还没有被赋值的时候
            return true;
        }
        String current_value = redisTemplate.opsForValue().get(key);
        //超时了
        if (current_value.equals("")
                && Long.parseLong(current_value) < System.currentTimeMillis()) {
            String old_value = redisTemplate.opsForValue().getAndSet(key, value);
            return !old_value.equals("")
                    && old_value.equals(current_value);
        }
        return false;
    }


    //加锁
    public boolean lock1(String key, String value) {
        //setIfAbsent相当于jedis中的setnx，如果能赋值就返回true，如果已经有值了，就返回false
        //即：在判断这个key是不是第一次进入这个方法
        //第一次，即：这个key还没有被赋值的时候
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }


    public boolean lock2(String key, String value) {
        //setIfAbsent相当于jedis中的setnx，如果能赋值就返回true，如果已经有值了，就返回false
        //即：在判断这个key是不是第一次进入这个方法
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //第一次，即：这个key还没有被赋值的时候
            return true;
        }
        String current_value = redisTemplate.opsForValue().get(key);//①
        if (!current_value.equals("")
                //超时了
                && Long.parseLong(current_value) < System.currentTimeMillis()) {
            //②
            //返回true就能解决死锁
            return true;
        }
        return false;
    }
}
