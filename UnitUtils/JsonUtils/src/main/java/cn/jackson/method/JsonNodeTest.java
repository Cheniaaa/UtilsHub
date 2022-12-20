package cn.jackson.method;

import cn.jackson.pojo.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @Description :   Json树模型JsonNode
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/12/21 01:08:08
 * @Version : 1.0
 */

public class JsonNodeTest {
    private ObjectMapper objectMapper;

    @Before
    public void objectMapper() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Java对象转换为JsonNode
     */
    @Test
    public void classToJsonNode() throws JsonProcessingException {
        UserInfo userInfo = new UserInfo(2, "HJN", 21);
        JsonNode node = objectMapper.valueToTree(userInfo);
        System.out.println(node);
    }

    /**
     * JsonNode转换为Java对象
     */
    @Test
    public void JsonNodeToClass() throws JsonProcessingException {
        String jsonStr = "{\"id\":1,\"username\":\"NieChen\",\"age\":21}";
        JsonNode node = objectMapper.readTree(jsonStr);
        UserInfo userInfo = objectMapper.treeToValue(node, UserInfo.class);
        System.out.println(userInfo);
    }


    /**
     * JsonNode的基础用法
     * JsonNode是类似于树的形态，拥有相同类型的子节点
     */
    @Test
    public void useJsonNode() throws JsonProcessingException {
        String jsonStr = "{\"id\":1,\"name\":\"NieChen\",\"hobbies\":[\"eat\",\"play\",\"sleep\"],\"university\":{\"name\":\"HQU\",\"grade\":20,\"academy\":\"CST\"}}";
        System.out.println("==================================");

        //Json字符串生成JsonNode：readValue(str, JsonNode.class)和readTree(str)
        JsonNode jsonNode = objectMapper.readValue(jsonStr, JsonNode.class);
        JsonNode node = objectMapper.readTree(jsonStr);
        //JsonNode生成Json字符串
        String str = objectMapper.writeValueAsString(jsonNode);

        System.out.println("使用get方法：");
        //通过字段名获取子节点
        JsonNode nameNode = node.get("name");
        String name = nameNode.asText();
        System.out.println("name:" + name);

        JsonNode hobbiesNode = node.get("hobbies");
        JsonNode hobbiesArrNode = hobbiesNode.get(0);//根据索引获取数组的值
        String s = hobbiesArrNode.asText();
        System.out.println("hobby-1:" + s);

        JsonNode universityNode = node.get("university");
        JsonNode universityNameNode = universityNode.get("name");
        String universityName = universityNameNode.asText();
        System.out.println("universityName:" + universityName);

        System.out.println("==================================");
        System.out.println("使用at方法：");
        //使用路径获取值
        JsonNode atNode = node.at("/university/name");
        System.out.println("universityName：" + atNode.asText());
    }

    /**
     * 由于JsonNode不可变，所以需要使用其子类ObjectNode进行修改
     */
    @Test
    public void useObjectNode() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();

        //set方法：增加修改JsonNode
        String jsonStr = "{\"id\":1,\"name\":\"NieChen\",\"hobbies\":[\"eat\",\"play\",\"sleep\"],\"university\":{\"name\":\"HQU\",\"grade\":20,\"academy\":\"CST\"}}";
        JsonNode node = objectMapper.readTree(jsonStr);
        objectNode.set("child1", node);

        //put方法：增加字段
        objectNode.put("child2", 123);
        objectNode.put("child3", "nc");

        //remove方法：移出字段
        objectNode.remove("child3");

        //循环字段
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode jsonNode = objectNode.get(fieldName);
            System.out.println(fieldName + "——>" + jsonNode);
        }
        System.out.println(objectNode);

    }

}
