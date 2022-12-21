package cn.jackson.method;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/21 22:56:56
 * @Version : 1.0
 */

public class JsonParserTest {
    private ObjectMapper objectMapper;

    @Before
    public void objectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Jackson JsonParser类是一个底层一些的JSON解析器
     * Jackson JsonParser的运行层级低于Jackson ObjectMapper。 这使得JsonParser比ObjectMapper更快，但使用起来也比较麻烦
     * JsonParser主要用于Json转换为Java对象
     */
    @Test
    public void useJsonParser() throws IOException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        //使用objectMapper创建
        try (JsonParser parser = objectMapper.createParser(jsonStr)) {
            System.out.println(parser);
        }
        //使用JsonFactory创建
        JsonFactory jsonFactory = new JsonFactory();
        try (JsonParser jsonParser = jsonFactory.createParser(jsonStr)) {
            UserInfo userInfo = new UserInfo();
            //到达末尾会返回false
            while (!jsonParser.isClosed()) {
                //获取token令牌
                JsonToken token = jsonParser.nextToken();
                System.out.print(token);

                //如果当前令牌为字段名则解析
                if (JsonToken.FIELD_NAME.equals(token)) {
                    String fieldName = jsonParser.currentName();
                    System.out.print(":" + fieldName);

                    //下一位即为字段值
                    jsonParser.nextToken();

                    //解析字段值
                    switch (fieldName) {
                        case "id" -> userInfo.setId(jsonParser.getIntValue());
                        case "username" -> userInfo.setUsername(jsonParser.getValueAsString());
                        case "age" -> userInfo.setAge(jsonParser.getValueAsInt());
                    }
                }
                System.out.println();
            }
            System.out.println(userInfo);
        }
    }
}
