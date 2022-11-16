package cn.esApi;

import cn.es.ElasticSearchApplication;
import cn.es.service.TbHotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/22 18:47:47
 * @Version : 1.0
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
public class TestApplication {
    @Autowired
    TbHotelService hotelService;


    @Test
    public void filterTest() {
//        Map<String, List<String>> filter = hotelService.filter();
//        System.out.println(filter);

    }
}
