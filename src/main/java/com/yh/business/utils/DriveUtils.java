package com.yh.business.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 开车工具包
 * @Since: YH007
 * @Date: 2020/3/22
 */
public class DriveUtils {

    private static Logger LOOGER = LoggerFactory.getLogger(DriveUtils.class);
    private static final String HTTP_BASE_URL = "https://www.javbus.cloud/";
    private static final String IMAGE_PATH = "E:\\Projects";


    /**
     * 获取车牌信息
     *
     * @param license
     */
    public static void getLicenseInfo(String license) {
        try {
            LOOGER.info("getLicenseInfo start, license: " + license);
            Document document = Jsoup.connect(HTTP_BASE_URL + license).get();
            //获取图片路径和标题
            Elements detail = document.select("body > div.container > div.row.movie > div.col-md-9.screencap > a > img");
            String picPath = detail.get(0).attr("src");
            String title = detail.get(0).attr("title");
            LOOGER.info("片名：" + title);
            String date = document.select("body > div.container > div.row.movie > div.col-md-3.info > p:nth-child(2)").text();
            LOOGER.info("发行日期：" + date);
            //查询角色名和角色连接地址
            //body > div.container > div.row.movie > div.col-md-3.info > p:nth-child(11) > span > a
            Element last = document.select("body > div.container > div.row.movie > div.col-md-3.info > p").last();
            Elements select = last.select("span > a");
            if (select != null) {
                String roleName = select.text();
                String roleHref = select.attr("href");
                LOOGER.info("演员：" + roleName);
                LOOGER.info("演员地址：" + roleHref);
            }

            downImages(picPath, license, "cover");
            LOOGER.info(license + " 封面保存到: " + IMAGE_PATH + File.separator + license);

            if (document.select("#sample-waterfall") != null && document.select("#sample-waterfall").size() > 0) {
                Elements picsElements = document.select("#sample-waterfall").get(0).children();
                for (Element picNode : picsElements) {
                    String href = picNode.attr("href");
                    downImages(href, license, "inner");
                }
                LOOGER.info(license + " 样品图片保存到: " + IMAGE_PATH + File.separator + license);
            }
            LOOGER.info("getLicenseInfo success, license: " + license);
        } catch (IOException e) {
            e.printStackTrace();
            LOOGER.error("系统异常：", e);
        }
    }


    /**
     * 下载图片
     *
     * @param imgUrl
     */
    private static void downImages(String imgUrl, String license, String type) {
        String licensePicPath = IMAGE_PATH + File.separator + license;
        File imgFilePath = new File(licensePicPath);
        //如果目录不存在，创建目录
        if (!imgFilePath.exists()) {
            boolean mkdir = imgFilePath.mkdir();
            LOOGER.info(mkdir ? "程序已自动创建目录" + imgFilePath.getPath() : "创建目录失败");
        }
        String imgName = "";
        if ("inner".equals(type)) {
            //拼接image下载地址
            String beforeUrl = imgUrl.substring(0, imgUrl.lastIndexOf("/") + 1);
            imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
            String newImgName = imgName;
            try {
                newImgName = URLEncoder.encode(imgName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                LOOGER.error("图片名称有误");
                e.printStackTrace();
            }
            imgUrl = beforeUrl + newImgName;
        } else {
            imgName = license + ".jpg";
        }
        try {
            //获取下载地址
            InputStream in = null;
            OutputStream out = null;
            try {
                in = HttpUtils.doGetPicture(imgUrl);
                //创建下载到本地的文件
                File imgFile = new File(licensePicPath, imgName);
                if (imgFile.exists()) {
                    LOOGER.info(imgName + " 该图片已存在");
                } else {
                    //写入文件
                    out = new FileOutputStream(imgFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                    out.flush();
                }
            } catch (IOException e) {
                LOOGER.error("下载图片失败:", e);
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    LOOGER.error("读写流关闭出现异常");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            LOOGER.error("网址格式错误");
            e.printStackTrace();
        }
    }


    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @param parh
     * @return
     */
    public static boolean getFileExist(String fileName, String parh) {
        boolean result = false;
        List<Map<String, String>> fileDetailMaps = getFileDetailMaps(parh);
        for (Map<String, String> map : fileDetailMaps) {
            String oneFileName = map.get("fileName");
            oneFileName = oneFileName.toLowerCase().replace("-", "");
            fileName = fileName.toLowerCase().replace("-", "");
            if (oneFileName.contains(fileName)) {
                result = true;
                break;
            }
        }
        return result;

    }


    /**
     * 获取文件集合
     *
     * @param path
     * @return
     */
    public static List<Map<String, String>> getFileDetailMaps(String path) {
        File baseDir = new File(path);
        List<Map<String, String>> fileDetailList = new ArrayList<>();
        List<Map<String, String>> fileMaps = getFileMap(baseDir, fileDetailList);
        return fileMaps;
    }


    /**
     * 递归获取
     *
     * @param file
     * @param fileDetailList
     * @return
     */
    private static List<Map<String, String>> getFileMap(File file, List<Map<String, String>> fileDetailList) {
        File[] files = file.listFiles();
        if (null != files) {
            for (File oneFile : files) {
                if (oneFile.isDirectory()) {
                    getFileMap(oneFile, fileDetailList);
                } else {
                    Map<String, String> fileMap = new HashMap<>();
                    fileMap.put("fileName", oneFile.getName());
                    fileMap.put("fileSize", String.valueOf(oneFile.getTotalSpace()));
                    fileMap.put("filePath", oneFile.getAbsolutePath());
                    fileDetailList.add(fileMap);
                }

            }
        }
        return fileDetailList;
    }


    public static String formatLicense(String fileName) {

        fileName = fileName.toLowerCase().replaceAll("-", "").replaceAll("\\.", "");

        if (fileName.endsWith("mp4")) {
            fileName = fileName.replaceAll("mp4", "");
        } else if (fileName.endsWith("avi")) {
            fileName = fileName.replaceAll("avi", "");
        } else if (fileName.endsWith("mkv")) {
            fileName = fileName.replaceAll("mkv", "");
        } else if (fileName.endsWith("wmv")) {
            fileName = fileName.replaceAll("wmv", "");
        } else if (fileName.endsWith("mov")) {
            fileName = fileName.replaceAll("mov", "");
        }

        //abs141c
        fileName = removeSuffix(fileName);


        int length = fileName.length();
        String number = fileName.substring(length - 3, length);

        String type = fileName.substring(0, length - 3);

        fileName = type + "-" + number;

        return fileName;

    }

    private static String removeSuffix(String fileName) {
        int length = fileName.length();

        String substring = fileName.substring(length - 1, length);
        if (substring.matches("[A-Za-z]")) {

            fileName = fileName.substring(0, length - 1);

            fileName = removeSuffix(fileName);

        } else {
            return fileName;
        }

        return fileName;
    }


    /**
     * 打开本地视频
     *
     * @param filePath
     */
    public static void openVideo(String filePath) {
        Runtime rn = Runtime.getRuntime();
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
}
