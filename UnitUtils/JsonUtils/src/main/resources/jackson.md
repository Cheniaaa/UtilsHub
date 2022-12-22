# Jackson

## 1.概要

jackson-core模块提供了两种处理JSON的方式（纵缆整个Jackson共三种）

1. 流式API：读取并将JSON内容写入作为离散事件 -> **JsonParser读取数据**，而**JsonGenerator负责写入数据**
2. 树模型：JSON文件在内存里以**树形式**表示。此种方式也很灵活，它类似于XML的DOM解析，层层嵌套（JsonNode）

![流式API](https://img-blog.csdnimg.cn/20200715184404566.png)

## 2.JSON转Java对象

### 1.主要使用方法：**objectMapper.readValue()**

部分特殊的情况：</br>

```text
List<UserInfo> userInfoList=objectMapper.readValue(strBytes,new TypeReference<List<UserInfo>>(){});
Map<String, Object> map=objectMapper.readValue(jsonStr,new TypeReference<Map<String, Object>>(){});
```

### 2.自定义反序列化器

> 需要继承**JsonDeserializer** 或者 **StdDeserializer**

```java
public class UserInfoDeserializer extends JsonDeserializer<UserInfo> {
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
```

使用@JsonDeserialize()注解或者使用**SimpleModule类**添加反序列化器

### 3.反序列化配置

```text
//忽略未知字段
objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//当json字符串中包含null则抛出异常
objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
```

也可以使用注解@JsonFormat(pattern = "yyyy-MM-dd")，@JsonIgnore等注解做一些配置

## 3.Java对象转Json
### 1.主要方法
```text
object.writeValue()
object.writeValueAsString()
object.writeValueAsBytes()
```
### 自定义序列化
> 需要继承**JsonSerializer** 或者 **StdSerializer**
```java
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
```
## 4.JsonNode树模型
### 1.关系转换
```text
objectMapper.readTree()：JSON -> JsonNode
objectMapper.writeValueAsString：JsonNode -> JSON

objectMapper.treeToValue()：JsonNode -> JavaBean
objectMapper.valueToTree()：JavaBean -> JsonNode
```
### 2.JsonNode使用
```text
主要方法：
node.get()：参数为字段名或是索引，根据字段名获取子节点或者根据索引获得数组中的值节点
node.asText()：将节点的字段值转换为字符串，相对应还有asInt()等
node.at()：参数为路径（"/xx/xx"），通过路径获取节点
```
### 3.ObjectNode使用
> JsonNode不可修改，ObjectNode是JsonNode的子类，可以对树模型进行修改
```text
主要方法：
objectNode.set()：对某个字段值的节点进行增加或者修改
objectNode.put()：增加字段名以及字段值（自动转换为节点）
objectNode.remove()：删除某字段名的节点
objectNode.fieldNames()：获取所有的字段名（返回值为迭代器），可以进行循环操作
```
## 5.JsonParser
> - Jackson JsonParser类是一个底层一些的JSON解析器
> - Jackson JsonParser的运行层级低于Jackson ObjectMapper。 这使得JsonParser比ObjectMapper更快，但使用起来也比较麻烦
> - JsonParser主要用于JSON转换为Java对象

```text
使用工厂创建JsonParser（也可以使用ObjectMapper）：
JsonFactory jsonFactory = new JsonFactory()
JsonParser jsonParser = jsonFactory.createParser(jsonStr)

主要方法：
jsonParser.isClosed()：判断是否到达末尾，结束为true（用于while循环）
jsonParser.nextToken()：获取下一个令牌
jsonParser.currentName()：获取当前令牌的名字（主要用于获取字段名）
jsonParser.getValueAsString()：获取当前令牌的值（主要用于获取字段值）
```
## 6.JsonGenerator
> JsonGenerator用于从Java对象（或代码从中生成JSON的任何数据结构）生成JSON
```text
使用工厂创建JsonGenerator：
JsonFactory factory = new JsonFactory();
JsonGenerator generator = factory.createGenerator(System.out, JsonEncoding.UTF8)

主要方法：
generator.writeStartObject()：初始标志
generator.writeEndObject()：结束标志
generator.writeStringField()：参数为字段名和字段值，用于字符串写入（同理writeNumberField等）
generator.writeObjectField()：对象类型的JSON写入（注意需要搭配generator.setCodec()，可以自定义编解码器，也可以直接使用ObjectMapper）
```
