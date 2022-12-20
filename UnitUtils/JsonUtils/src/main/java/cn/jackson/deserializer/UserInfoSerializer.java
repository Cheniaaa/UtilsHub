package cn.jackson.deserializer;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/20 01:38:38
 * @Version : 1.0
 */

public class UserInfoSerializer extends JsonSerializer<UserInfo> {
    @Override
    public void serialize(UserInfo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("username", value.getUsername().toUpperCase());
        gen.writeNumberField("age", value.getAge());
        gen.writeEndObject();
    }
}
