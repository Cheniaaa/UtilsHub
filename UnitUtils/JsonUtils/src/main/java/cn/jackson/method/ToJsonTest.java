package cn.jackson.method;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/17 23:45:45
 * @Version : 1.0
 */

public class ToJsonTest {
    private ObjectMapper objectMapper;

    @Before
    public void objectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * 序列化成json字符串或者字符数组
     */
    @Test
    public void classToJsonStrAndBytes() throws JsonProcessingException {
        UserInfo userInfo = new UserInfo(1, "ZhangSan", 20);
        String jsonStr = objectMapper.writeValueAsString(userInfo);
        System.out.println(jsonStr);
        byte[] jsonBytes = objectMapper.writeValueAsBytes(userInfo);
        System.out.println(Arrays.toString(jsonBytes));
    }

    /**
     * 序列化成json文件
     */
    @Test
    public void classToJsonFile() throws Exception {
        UserInfo userInfo = new UserInfo(1, "ZhangSan", 20);
        objectMapper.writeValue(new FileOutputStream("src/main/java/cn/jackson/UserInfoSerializer.json"), userInfo);
    }
}
