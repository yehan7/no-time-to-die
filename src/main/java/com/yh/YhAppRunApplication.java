package com.yh;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by idea China
 * Author: YH007
 * Time: 19:07 2020/1/22
 * Description:
 */
@SpringBootApplication(scanBasePackages = "com.yh.*")
@MapperScan("com.yh.*.mapper")
@EnableScheduling
@EnableRabbit
@EnableAsync
@ServletComponentScan(basePackages = "com.yh.service.filters")
public class YhAppRunApplication {

    private static Logger LOGGER = LoggerFactory.getLogger(YhAppRunApplication.class);

    public static void main(String[] args) {
        LOGGER.info("com.yh.YhAppRunApplication start ....");
        SpringApplication.run(YhAppRunApplication.class, args);
        LOGGER.info("com.yh.YhAppRunApplication success");
    }

}
