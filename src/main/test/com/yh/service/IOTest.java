package com.yh.service;

import com.jcraft.jsch.SftpException;
import com.yh.business.entity.SFTPConfig;
import com.yh.business.utils.ExcelUtils;
import com.yh.business.utils.ExeUtils;
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

        File file = new File("C:\\Users\\YH\\OneDrive\\桌面\\mall.sql");
        sftpUtils.getDefultChannelSftp().upload(null, file);

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


    public static void main(String[] args) {
        /*Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            //执行的文件的位置
            p = rt.exec("D:\\Program Files (x86)\\Mobatek\\MobaXterm\\MobaXterm.exe");
            System.out.println("成功打开软件和文件！");

        } catch (Exception e) {
            System.out.println("打开软件失败");
            e.printStackTrace();
        }*/

        //String file = "S:\\Protected\\Java\\（2）Java相关\\尚硅谷Shiro视频\\【更多资源，微信公众号：cplus人工智能算法后端技术】1.尚硅谷_Shiro_简介.avi";
        /*ExeUtils.openVideo(file);*/
        ExeUtils.openSSH();
    }



}
