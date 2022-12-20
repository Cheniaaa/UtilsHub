package cn.mapstruct.mapper.mapping;

import cn.mapstruct.model.dto.UserInfoDto;
import cn.mapstruct.model.param.UserInfoParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/12 01:22:22
 * @Version : 1.0
 */
@Mapper
public interface UserInfoParamToDtoMapper {

    UserInfoParamToDtoMapper INSTANCE = Mappers.getMapper(UserInfoParamToDtoMapper.class);


    @Mappings({
            @Mapping(source = "id", target = "userId"),
            @Mapping(source = "name", target = "userName"),
            @Mapping(source = "phone", target = "phoneNumber")
    })
    UserInfoDto toUserInfoDot(UserInfoParam userInfoParam);

}
