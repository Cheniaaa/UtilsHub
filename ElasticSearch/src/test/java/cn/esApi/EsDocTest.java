package cn.esApi;

import cn.es.ElasticSearchApplication;
import cn.es.dto.HotelDoc;
import cn.es.entity.TbHotel;
import cn.es.service.TbHotelService;
import com.alibaba.fastjson2.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * @Description : 文档操作（相当于mysql的数据操作）
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/20 19:25:25
 * @Version : 1.0
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
public class EsDocTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private TbHotelService hotelService;

    //新增文档
    @Test
    public void addDoc() throws IOException {
        IndexRequest indexRequest = new IndexRequest("hotel").id("1");

        TbHotel hotel = hotelService.getById(47478);
        HotelDoc hotelDoc = new HotelDoc(hotel);

        indexRequest.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    //获取文档
    @Test
    public void getDoc() throws IOException {
        GetRequest getRequest = new GetRequest("hotel").id("1");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    //更新文档
    @Test
    public void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("hotel", "1");
        updateRequest.doc(
                "starName", "四钻",
                "price", 429
        );
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除文档同理


    //批量新增数据
    @Test
    public void bulkAddDoc() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        List<TbHotel> hotelList = hotelService.list();
        hotelList.forEach(v -> {
            bulkRequest.add(new IndexRequest("hotel").id(v.getId().toString()).source(JSON.toJSONString(new HotelDoc(v)), XContentType.JSON));
        });
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }
}
