package com.yh.business.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 调用本地可执行文件
 * @Since: YH007
 * @Date: 2020/3/22
 */
public class ExeUtils {

    private static Logger LOOGER = LoggerFactory.getLogger(ExeUtils.class);

    private static Runtime rn = Runtime.getRuntime();

    /**
     * 打开本地视频
     *
     * @param filePath
     */
    public static void openVideo(String filePath) {
        Process p = null;
        try {
            p = rn.exec("D:\\Program Files\\DAUM\\PotPlayer\\PotPlayerMini64.exe " + filePath);
            if (p.isAlive()) {
                LOOGER.info("openVideo success");
            }
        } catch (Exception e) {
            LOOGER.error("openVideo error", e);
        }
    }

    public static void openSSH() {
        Process p = null;
        try {
            p = rn.exec("D:\\Program Files (x86)\\Mobatek\\MobaXterm\\MobaXterm.exe");
            if (p.isAlive()) {
                LOOGER.info("openSSH success");
            }
        } catch (Exception e) {
            LOOGER.error("openSSH error", e);
        }
    }
}
