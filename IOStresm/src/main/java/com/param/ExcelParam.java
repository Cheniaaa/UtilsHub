package com.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * @Description : 存放Excel表的数据
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/17  18:26:26
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelParam<T> {

    /**
     * 标题
     */
    private String title;


    /**
     * 文件名
     */
    private String fileName;


    /**
     * 表头
     */
    private List<String> header;


    /**
     * 存放数据
     */
    private List<T> data;

    private HSSFWorkbook wb;

    private HSSFSheet sheet;

}
