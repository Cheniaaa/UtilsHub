package common.utils;

import common.param.ExcelDataParam;
import common.param.ExcelParam;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Description : Excel工具类
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/16  09:56:56
 * @Version : 1.0
 */

public class ExcelUtil {


    public static ExcelParam<ExcelDataParam> createExcel(ExcelParam<ExcelDataParam> excelParam) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        createTitle(wb, sheet, excelParam.getTitle(), excelParam.getHeader());

        excelParam.setWb(wb);
        excelParam.setSheet(sheet);

        return excelParam;

    }

    /**
     * 创建标题和表头
     */
    private static void createTitle(HSSFWorkbook workbook, HSSFSheet sheet, String title, List<String> header) {
        //用CellRangeAddress合并单元格，在这里作为sheet中的标题
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, header.size() - 1);
        sheet.addMergedRegion(region);

        HSSFRow row_title = sheet.createRow(0);
        row_title.setHeightInPoints(26);
        HSSFCell cell_title;
        cell_title = row_title.createCell(0);
        cell_title.setCellValue(title);

        //设置标题样式:居中加粗
        HSSFCellStyle style_title = workbook.createCellStyle();
        HSSFFont font_title = workbook.createFont();
        font_title.setBold(true);
        font_title.setFontHeightInPoints((short) 20);
        font_title.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        style_title.setFont(font_title);
        style_title.setAlignment(HorizontalAlignment.CENTER);
        style_title.setVerticalAlignment(VerticalAlignment.CENTER);
        cell_title.setCellStyle(style_title);


        //以下为表头
        HSSFRow row = sheet.createRow(1);
        //设置行高
        row.setHeightInPoints(18);
        //设置列宽度
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        sheet.setColumnWidth(2, 30 * 256);
        //设置样式:居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //添加字段
        HSSFCell cell;
        for (String head : header) {
            cell = row.createCell(header.indexOf(head));
            cell.setCellValue(head);
            cell.setCellStyle(style);
        }
    }

    public static void addData(HSSFSheet sheet, Integer row, Object... values) {

        for (int i = 0; i < values.length; i++) {
            HSSFRow sheetRow = sheet.createRow(row + 1);
            sheetRow.createCell(i).setCellValue(String.valueOf(values[i]));
        }
    }


    public static void downloadExcel(ExcelParam<ExcelDataParam> excelParam, HttpServletResponse response) {
        try {
            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            //设置文件头
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + new String(excelParam.getFileName().getBytes(StandardCharsets.UTF_8),
                            "ISO8859-1"));
            response.setContentType("application/msexcel");
            excelParam.getWb().write(output);
            excelParam.getWb().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
