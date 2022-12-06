package common.service;

import common.entity.Product;
import common.param.ExcelDataParam;
import common.param.ExcelParam;
import common.utils.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description : 导出报表Service
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/16  10:11:11
 * @Version : 1.0
 */

@Service
@RequiredArgsConstructor
public class ExcelService {


    /**
     * 表格生成
     *
     * @param productList
     * @param fileName
     * @param title
     * @param response
     */
    public void exportExcel(List<Product> productList, String fileName, String title, HttpServletResponse response) {

        ExcelParam<ExcelDataParam> dataParamExcelParam = dataHandler(productList, fileName, title);

        ExcelParam<ExcelDataParam> excel = createExcel(dataParamExcelParam, response);
        ExcelUtil.downloadExcel(excel, response);
    }


    public ExcelParam<ExcelDataParam> createExcel(ExcelParam<ExcelDataParam> excelParam, HttpServletResponse response) {

        ExcelParam<ExcelDataParam> excel = ExcelUtil.createExcel(excelParam);

        int i = 1;
        for (ExcelDataParam datum : excel.getData()) {
            ExcelUtil.addData(excel.getSheet(),
                    i,
                    datum.getName(),
                    datum.getOriginalPrice(),
                    datum.getPromotePrice(),
                    datum.getStock());
            i++;
        }

        return excel;
    }

    /**
     * 自定义数据处理部分
     *
     * @param dataList
     * @return
     */
    public ExcelParam<ExcelDataParam> dataHandler(List<Product> dataList, String fileName, String title) {
        ExcelParam<ExcelDataParam> dataParamExcelParam = new ExcelParam<>();
        dataParamExcelParam.setFileName(fileName);
        dataParamExcelParam.setTitle(title);
        dataParamExcelParam.setHeader(Arrays.asList("产品名称", "产品原价", "产品现价", "产品库存"));

        ArrayList<ExcelDataParam> excelDataParams = new ArrayList<>();
        for (Product data : dataList) {
            ExcelDataParam excelDataParam = new ExcelDataParam();
            excelDataParam.setName(data.getPName());
            excelDataParam.setOriginalPrice(data.getPOriginalPrice());
            excelDataParam.setPromotePrice(data.getPPromotePrice());
            excelDataParam.setStock(data.getPStock());
            excelDataParams.add(excelDataParam);
        }
        dataParamExcelParam.setData(excelDataParams);

        return dataParamExcelParam;
    }

}
