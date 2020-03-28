package com.yh.service;

import com.yh.business.utils.EncryptUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EncryptUtilsTest {

    @Test
    public void test() {

        String xx = EncryptUtils.encryptByBase64("肖杏");
        String yh = EncryptUtils.encryptByBase64("叶晗");
        String s = EncryptUtils.decryptByBase64("5Y+25pmX");
        System.out.println(xx);
        System.out.println(yh);
        System.out.println(s);
    }



}
