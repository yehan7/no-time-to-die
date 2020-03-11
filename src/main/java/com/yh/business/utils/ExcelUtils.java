package com.yh.business.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by idea China
 * Author: YH007
 * Time: 19:22 2020/2/6
 * Description:
 */
public class ExcelUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);


    /**
     * 获取到workbook
     *
     * @param: filePath  filePath
     * @return: org.apache.poi.xssf.usermodel.XSSFWorkbook
     */
    public static XSSFWorkbook getWorkbookByPath(String filePath) {
        FileInputStream fis;
        XSSFWorkbook workbook = null;
        try {
            fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        } catch (Exception e) {
            LOGGER.error("ExcelUtils getWorkbook error", e);
        }
        return workbook;
    }


    /**
     * 获取单元格的值
     *
     * @param: cell  cell
     * @return: java.lang.String
     */
    //获取单元格各类型值，返回字符串类型
    public static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "空值";
        }
        String cellValue = "";
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING: //字符串类型
                cellValue = cell.getStringCellValue().trim();
                cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
                break;
            case BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case NUMERIC: //数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    cellValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
                break;
            default: //其它类型，取空串吧
                cellValue = "";
                break;
        }
        return cellValue;
    }
}
