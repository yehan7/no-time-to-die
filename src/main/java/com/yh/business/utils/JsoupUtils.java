package com.yh.business.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/22
 */
public class JsoupUtils {
    private static Logger LOOGER = LoggerFactory.getLogger(JsoupUtils.class);
    private static final String HTTP_BASE_URL = "";
    private static final String IMAGE_PATH = "";


    public static void getDocument(String url) {
        try {
            LOOGER.info("getDocument start, url: " + url);
            Document document = Jsoup.connect(url).get();
        } catch (Exception e) {
            LOOGER.info("系统异常：", e);
        }
    }
}
