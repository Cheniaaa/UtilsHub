package common.controller;

import common.entity.Product;
import common.service.ExcelService;
import common.service.poService.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description : 导出报表控Controller
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/16  09:36:36
 * @Version : 1.0
 */

@RestController
@RequestMapping("/IOStream/export")
@RequiredArgsConstructor
@Api(tags = "导出报表")
public class ExcelController {


    private final ExcelService excelService;

    private final ProductService productService;


    @GetMapping("/exportExcel")
    @ApiOperation("导出报表")
    public void exportExcel(@RequestParam String fileName, @RequestParam String title, HttpServletResponse response) {
        List<Product> productList = productService.list();
        excelService.exportExcel(productList, fileName, title, response);
    }
}
