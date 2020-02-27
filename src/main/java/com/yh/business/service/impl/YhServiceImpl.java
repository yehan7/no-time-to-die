package com.yh.business.service.impl;

import com.yh.business.service.YhService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by idea China
 * Author: YH007
 * Time: 20:10 2020/2/9
 * Description:
 */
@Service
public class YhServiceImpl implements YhService {
    @Override
    public void get() {
        System.out.println("业务处理");
    }



    @RabbitListener(queues = "yh.news")
    public void getMap(Map map){
        System.out.println(map);
    }

    @RabbitListener(queues = "yh.news")
    public void getMap(Message message){
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
