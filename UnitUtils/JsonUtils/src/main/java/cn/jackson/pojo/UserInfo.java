package cn.jackson.pojo;

import cn.jackson.deserializer.UserInfoDeserializer;
import cn.jackson.deserializer.UserInfoSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/17 23:22:22
 * @Version : 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonDeserialize(using = UserInfoDeserializer.class)
//@JsonSerialize(using = UserInfoSerializer.class)
public class UserInfo implements Serializable {
    private Integer id;
    private String username;
    private Integer age;



}
