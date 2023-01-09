package cn.logback.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2023/1/9 02:10:10
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoParam {
    private String name;
    private Integer age;
}
