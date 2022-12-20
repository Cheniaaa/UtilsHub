package cn.mapstruct.test;

import cn.mapstruct.mapper.mapping.UserInfoParamToDtoMapper;
import cn.mapstruct.model.dto.UserInfoDto;
import cn.mapstruct.model.param.UserInfoParam;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/12 01:32:32
 * @Version : 1.0
 */

public class UserInfoTest {
    public static void main(String[] args) {
        UserInfoParam userInfoParam = new UserInfoParam(1, "NieChen", "Wannabe", "123456", "15888962237");
        UserInfoParamToDtoMapper userInfoParamToDtoMapper = UserInfoParamToDtoMapper.INSTANCE;
        UserInfoDto userInfoDto = userInfoParamToDtoMapper.toUserInfoDot(userInfoParam);
        System.out.println(userInfoDto);
    }
}
