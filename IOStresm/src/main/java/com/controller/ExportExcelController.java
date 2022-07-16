package com.controller;

import com.service.ExportExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ExportExcelController {



    private final ExportExcelService excelService;


    @GetMapping("/exportExcel")
    @ApiOperation("导出报表")
    public void exportExcel() {
        excelService.exportExcel();
    }
}
