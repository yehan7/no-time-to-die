package com.yh.business.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by idea China
 * Author: YH007
 * Time: 21:55 2020/2/9
 * Description:
 */
@Component
@Aspect
public class TestAop {

    Logger LOGGER = LoggerFactory.getLogger(TestAop.class);


    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(* com.yh.business.controller.YhController.*(..))")
    public void testAspect(){

    }

    @Before("testAspect()")
    public void check(JoinPoint joinPoint){
        System.out.println("执行业务例行检查。。。。");
        LOGGER.info("执行业务例行检查。。。。");
    }
    @After("testAspect()")
    public void log(){
        System.out.println("例行记录日志。。。。");
        LOGGER.info("例行记录日志。。。。");

    }

}
