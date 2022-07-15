package JSONUtils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @Description : 关于JSONObject的操作
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/13  15:35:35
 * @Version : 1.0
 */

public class JsonObjectUtils {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("聂晨", 1);
        map.put("cc", 2);
        map.put("aa", 3);
        map.put("bb", 4);
        JSONObject result = new JSONObject();
        result.set("1", map);
        JSONArray keyArray = new JSONArray();
        keyArray.put(map.keySet());
        System.out.println(result);
        System.out.println(keyArray.get(0));

    }
}
