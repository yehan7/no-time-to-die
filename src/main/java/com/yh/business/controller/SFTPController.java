package com.yh.business.controller;

import com.yh.business.utils.SFTPUtils;
import com.yh.business.vo.CommonResult;
import com.yh.business.vo.ResultOutVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
    public CommonResult uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        CommonResult result = new CommonResult();
        try {
            String path = request.getParameter("path");
            File uploadFile = multipartFileToFile(file);
            sftpUtils.getDefultChannelSftp().upload(path, uploadFile);
        } catch (Exception e) {
            LOGGER.error("uploadfile error", e);
            result.setResultCode("500");
            result.setDesc("uploadfile error");
            return result;
        }
        result.setResultCode("200");
        result.setDesc("uploadfile success");
        LOGGER.info("uploadfile success");
        return result;
    }


    /**
     * multipartFile转化成file
     *
     * @param file
     * @return
     */
    public File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    /**
     * inputStreamToFile
     * @param ins
     * @param file
     */
    private void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
