package cn.mapstruct.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/12 01:09:09
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
public class UserInfoParam {

    private Integer id;

    private String name;

    private String nickName;

    private String password;

    private String phone;

}
