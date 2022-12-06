import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description : 测试
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/7/18 15:01:01
 * @Version : 1.0
 */

public class Test {

    /**
     * @return java.lang.String
     * @Param : [java.lang.String, java.lang.Integer]
     * @Description : get
     * @Author : 聂晨
     * @Date : 2022/7/18 15:05:09
     */


    public static void main(String[] args) {
        String s = ",2,5";
        List<String> list = Arrays.asList(s.split(","));
        System.out.println(list.get(0));
    }
}
