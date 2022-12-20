package cn.jackson.method;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/17 23:21:21
 * @Version : 1.0
 */

public class ToClassTest {

    private ObjectMapper objectMapper;

    @Before
    public void objectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Json字符串转换为类
     */
    @Test
    public void jsonStrToClass() throws JsonProcessingException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        UserInfo userInfo = objectMapper.readValue(jsonStr, UserInfo.class);
        System.out.println(userInfo);

    }

    /**
     * Json字符流转换为类
     */
    @Test
    public void jsonCharToClass() throws IOException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        Reader reader = new StringReader(jsonStr);
        UserInfo userInfo = objectMapper.readValue(reader, UserInfo.class);
        System.out.println(userInfo);
    }

    /**
     * Json文件转换为类
     */
    @Test
    public void jsonFileToClass() throws IOException {
        File file = new File("src/main/java/cn/jackson/UserInfo.json");
        UserInfo userInfo = objectMapper.readValue(file, UserInfo.class);
        System.out.println(userInfo);
    }

    /**
     * Json字节流转换为类
     */
    @Test
    public void jsonInputStreamToClass() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/main/java/cn/jackson/UserInfo.json");
        UserInfo userInfo = objectMapper.readValue(inputStream, UserInfo.class);
        System.out.println(userInfo);
    }

    /**
     * Json二进制数组转换为类
     */
    @Test
    public void jsonArrayToClass() throws IOException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        byte[] strBytes = jsonStr.getBytes(StandardCharsets.UTF_8);
        UserInfo userInfo = objectMapper.readValue(strBytes, UserInfo.class);
        System.out.println(userInfo);
    }

    /**
     * Json数组字符串转换为对象数组
     */
    @Test
    public void jsonArrStrToClassArr() throws IOException {
        String jsonStr = "[{\"id\":1,\"username\":\"NieChen\",\"age\":21},{\"id\":2,\"username\":\"HJN\",\"age\":21}]";
        byte[] strBytes = jsonStr.getBytes(StandardCharsets.UTF_8);
        UserInfo[] userInfoArr = objectMapper.readValue(strBytes, UserInfo[].class);
        System.out.println(Arrays.toString(userInfoArr));

        //也可以直接转换成List类型
        List<UserInfo> userInfoList = objectMapper.readValue(strBytes, new TypeReference<List<UserInfo>>() {
        });
        System.out.println(userInfoList);
    }


    /**
     * Json字符串转换为Map类型
     */
    @Test
    public void jsonStrToMap() throws IOException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        Map<String, Object> map = objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(map);
    }

    /**
     * Json转类的配置
     */
    @Test
    public void jsonConfig() throws IOException {
        //忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //当json字符串中包含null则抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
    }


}
