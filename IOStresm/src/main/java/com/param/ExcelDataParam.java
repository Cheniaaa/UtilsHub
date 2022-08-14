package com.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description : 存放Excel数据（自定义）
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/17  21:39:39
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDataParam {
    private String name;
    private Double originalPrice;
    private Double promotePrice;
    private Integer stock;
}
