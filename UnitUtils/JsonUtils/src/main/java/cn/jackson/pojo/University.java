package cn.jackson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/22 22:32:32
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {
    private String name;
    private String address;
    private Integer grade;
}
