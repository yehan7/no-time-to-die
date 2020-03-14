package com.yh.service;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.yh.business.entity.SFTPConfig;
import com.yh.business.utils.ExcelUtils;
import com.yh.business.utils.SFTPUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

;

/**
 * Created by idea China
 * Author: YH007
 * Time: 18:07 2020/2/6
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IOTest {

    private static Logger LOGGER = LoggerFactory.getLogger(IOTest.class);

    @Test
    public void test1() {
        FileOutputStream fos = null;
        XSSFWorkbook workbook = null;
        try {
     /*       String path = IOTest.class.getClassLoader().getResource("/").getPath();
            System.out.println("path:" + path); */
            String filePath = "C:\\Users\\YH\\OneDrive\\桌面\\test.xlsx";
            workbook = ExcelUtils.getWorkbookByPath(filePath);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            System.out.println(sheet.getRow(0).getCell(0).getStringCellValue());
            int num = 0;
            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(1);
                String cellValue = ExcelUtils.getCellValueByCell(cell);
                if (!"正常".equals(cellValue)) {
                    cell.setCellValue("正常");
                }
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                cell.setCellStyle(cellStyle);
                System.out.println(ExcelUtils.getCellValueByCell(cell));
                num++;
            }
            System.out.println("总行数：" + lastRowNum);
            System.out.println("总人数：" + num);
            fos = new FileOutputStream("C:\\Users\\YH\\OneDrive\\桌面\\test2.xlsx");
            workbook.write(fos);
        } catch (Exception e) {
            LOGGER.error("系统错误：", e);
        } finally {

            try {
                fos.flush();
                fos.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Autowired
    static SFTPConfig sftpConfig;

    @Autowired
    SFTPUtils sftpUtils;

    @Test
    public void ftpUpload() throws FileNotFoundException, SftpException {

       /* File file = new File("C:\\Users\\YH\\OneDrive\\桌面\\mall.sql");
        sftpUtils.getDefultChannelSftp().upload(null, file);*/

        /*Vector<?> objects = sftpUtils.getDefultChannelSftp().listFiles("/root/temp");
        objects.forEach(System.out::println);
*/
        //File file = new File("C:\\Users\\YH\\OneDrive\\桌面\\linux启动命令.txt");
        //boolean b = ftpUtils.uploadFile(file);


     /*   Vector<ChannelSftp.LsEntry> lsEntries = sftpUtils.getDefultChannelSftp().listFiles("/root/temp");

        for (ChannelSftp.LsEntry entry : lsEntries){
            String fileName = entry.getFilename();
            System.out.println(fileName);
        }*/

        //sftpUtils.getDefultChannelSftp().download("/root/temp", "武汉住房公积金接收函.jpg", "C:\\Users\\YH\\OneDrive\\桌面\\a.jpg");

        sftpUtils.getDefultChannelSftp().delete("/root/temp", "mall.sql");
    }


    @Test
    public void ffsfs() {
        String host = sftpConfig.getHost();
        System.out.println(host);
    }
}
