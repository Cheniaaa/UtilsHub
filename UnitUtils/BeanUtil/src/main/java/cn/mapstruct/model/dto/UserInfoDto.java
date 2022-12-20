package cn.mapstruct.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/12 01:19:19
 * @Version : 1.0
 */
@Data
@AllArgsConstructor
public class UserInfoDto {
    private Integer userId;
    private String userName;
    private String phoneNumber;
}
