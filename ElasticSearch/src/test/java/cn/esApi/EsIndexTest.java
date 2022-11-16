package cn.esApi;

import cn.es.ElasticSearchApplication;
import cn.es.constants.HotelConstants;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @Description : 索引操作
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/20 01:43:43
 * @Version : 1.0
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
public class EsIndexTest {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引
    @Test
    public void creatIndex() throws IOException {
        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("hotel");
        createIndexRequest.source(HotelConstants.HotelMapping, XContentType.JSON);
        //执行请求
        IndicesClient indices = restHighLevelClient.indices();
        indices.create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexRequest);
    }

    //是否存在索引
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("hotel");
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引
    @Test
    public void delIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hotel");
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(acknowledgedResponse.isAcknowledged());

    }
}
