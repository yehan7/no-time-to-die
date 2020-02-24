package com.yh.business.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {



    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 文件路径（如：F:/a/b/test.txt）
     */
    private static File createFile(String path) {
        // 创建文件夹
        if (path.contains("/")) {
            String[] split = path.split("/");
            String fileName = split[split.length - 1];
            String dirPath = path.replace(fileName, "");
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
