package com.yh.business.service.impl;

import com.yh.business.service.YhService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/2/9
 */
@Service
public class YhServiceImpl implements YhService {
    @Override
    public void get() {
        System.out.println("业务处理");
    }


    @RabbitListener(queues = "yh.news")
    public void getMap(Map map) {
        System.out.println(map);
    }

    @RabbitListener(queues = "yh.news")
    public void getMap(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
