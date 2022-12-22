package cn.jackson.deserializer;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/19 20:16:16
 * @Version : 1.0
 */

public class UserInfoDeserializer extends JsonDeserializer<UserInfo>{
    @Override
    public UserInfo deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JsonProcessingException {
        UserInfo userInfo = new UserInfo();
        while (!p.isClosed()) {
            JsonToken jsonToken = p.nextToken();
            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String currentName = p.currentName();
                p.nextToken();
                switch (currentName) {
                    case "id" -> userInfo.setId(p.getIntValue());
                    case "username" -> userInfo.setUsername(p.getValueAsString().toLowerCase());
                    case "age" -> userInfo.setAge(p.getValueAsInt());
                }
            }
        }
        return userInfo;
    }
}
