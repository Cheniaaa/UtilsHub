package cn.jackson.method;

import cn.jackson.pojo.Student;
import cn.jackson.pojo.University;
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

        Student student = new Student("2025121023", "聂晨", new University("华侨大学", "厦门市", 20));
        //使用JsonFactory生成JsonGenerator,也可以直接使用objectMapper创建
        JsonFactory factory = new JsonFactory();
        try (JsonGenerator generator = factory.createGenerator(System.out, JsonEncoding.UTF8)) {
            generator.writeStartObject();
            generator.writeStringField("id", student.getId());
            generator.writeStringField("name", student.getName());

            //使用writeObjectField必须要指定解码器（可以使用自定义的解码器，也可以直接使用objectMapper）
            generator.setCodec(objectMapper);
            generator.writeObjectField("university", student.getUniversity());
            generator.writeEndObject();
        }
    }
}
