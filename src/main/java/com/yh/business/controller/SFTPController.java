package com.yh.business.controller;

import com.yh.business.utils.SFTPUtils;
import com.yh.business.vo.CommonResult;
import com.yh.business.vo.ResultOutVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;

/**
 * @Description:
 * @Since: YH007
 * @Date: 2020/3/14
 */
@RestController
@RequestMapping("/v1/sftp")
public class SFTPController {

    private static Logger LOGGER = LoggerFactory.getLogger(SFTPUtils.class);

    @Autowired
    private SFTPUtils sftpUtils;


    @PostMapping("/uploadfile")
    public CommonResult uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {

        LOGGER.info("开始上传");
        CommonResult result = new CommonResult();
        SFTPUtils defultChannelSftp = null;
        InputStream inputStream = null;
        String tempFilePath = File.separator + "root" + File.separator + "temp"+ System.currentTimeMillis() + File.separator;
        LOGGER.info("临时保存路径：" + tempFilePath);
        try {
            String path = request.getParameter("path");
            LOGGER.info("要上传的路径：" + path);
            //上传的临时文件
            File uploadFile = new File(tempFilePath + multipartFile.getOriginalFilename());
            inputStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
            //上传文件开始
            defultChannelSftp = sftpUtils.getDefultChannelSftp();
            LOGGER.info("上传的文件" + uploadFile.getAbsolutePath());
            defultChannelSftp.upload(path, uploadFile);
            result.setResultCode("200");
            result.setDesc("uploadfile success");
            LOGGER.info("uploadfile success");
            return result;
        } catch (Exception e) {
            LOGGER.error("uploadfile error", e);
            result.setResultCode("500");
            result.setDesc("uploadfile error");
            return result;
        } finally {
            try {
                FileUtils.deleteDirectory(new File(tempFilePath));
                LOGGER.info("删除文件夹成功：" + tempFilePath);
            } catch (IOException e) {
                LOGGER.error("删除零时文件夹失败:", e);
            }
            IOUtils.closeQuietly(inputStream);
            //defultChannelSftp.disconnect();
        }

    }


}
