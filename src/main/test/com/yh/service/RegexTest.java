package com.yh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by idea China
 * Author: YH007
 * Time: 14:52 2020/2/6
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegexTest {

    private static Logger LOGGER = LoggerFactory.getLogger(RegexTest.class);

    /*匹配————————————————————————————————————————————————————：
   测试手机号格式：
   * 1.长度为11位，全部都是数字
   * 2.第一位字符为“1”
   * 3.第二位3/5/7/9*/
    @Test
    public void test1() {
        String phoneNum = "132332344";
        String regex = "[1][35789][0-9]{9}";
        boolean matches = phoneNum.matches(regex);
        LOGGER.info("是否匹配：" + matches);
    }

    public static void main(String[] args) {

        String str = "肖杏说：我。。。我我。。我我我。。。。肖。，，。杏。。。就，，，。是。。。美女女。。。啊啊啊啊啊啊啊！";
        String s = str.replaceAll("(\\。)\\1*|(\\，)\\2+", "").replaceAll("(.)\\1+", "$1");
        System.out.println(s);

        String emailTitle = "script@122.189.11.123@东湖开发开发站点_result";


        String ques = "肖杏的*笑，像，，春天的花儿（？？）一般~";
        ques = ques.replaceAll("(([\\*\\（\\？\\）\\~])\\1*)|(\\，)\\3+", "");
        System.out.println(ques);
    }
}
