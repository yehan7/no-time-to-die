package com.yh.core.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class YHStartLoad implements CommandLineRunner
{

    @Autowired
    Cache cache;

    @Override
    public void run(String... args) throws Exception
    {


        // 放入缓存
        cache.put("yh", "007");
       /* // 获取缓存
        String value = (String) cache.getIfPresent("yh");
        System.out.println("value：" + value);*/
    }
}
