package com.yh.business.service.impl;

import com.yh.business.service.XxService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class XxServiceImpl implements XxService {


    @Override
    @Async
    public void testPool() {
        System.out.println("当前是线程：" + Thread.currentThread().getName());

    }
}
