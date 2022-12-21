package cn.jackson.method;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/21 23:27:27
 * @Version : 1.0
 */

public class JsonGeneratorTest {
    private ObjectMapper objectMapper;

    @Before
    public void objectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * JsonGenerator用于从Java对象（或代码从中生成JSON的任何数据结构）生成JSON
     */
    @Test
    public void useJsonGenerator() throws IOException {

        UserInfo userInfo = new UserInfo(1, "NieChen", 21);
        //使用JsonFactory生成JsonGenerator,也可以直接使用objectMapper创建
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator generator = factory.createGenerator(new File("src/main/java/cn/jackson/UserInfoSerializer.json"), JsonEncoding.UTF8)) {
            generator.writeStartObject();
            generator.writeNumberField("id", userInfo.getId());
            generator.writeStringField("username", userInfo.getUsername().toLowerCase());
            generator.writeNumberField("age", userInfo.getAge());
            generator.writeEndObject();
        }
    }
}
