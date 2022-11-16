package cn.esApi;

import cn.es.ElasticSearchApplication;
import cn.es.dto.HotelDoc;
import com.alibaba.fastjson2.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/21 01:39:39
 * @Version : 1.0
 */
@SpringBootTest(classes = ElasticSearchApplication.class)
public class EsSearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Test
    public void matchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel");
        searchRequest.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //抽取解析的方法
        handleResponse(response);
    }


    @Test
    public void bool() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel");

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("city", "上海"))
                .filter(QueryBuilders.rangeQuery("price").lte(250));

        searchRequest.source().query(boolQuery);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        handleResponse(response);
    }


    //分页和排序
    @Test
    public void pageAndSort() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel");
        searchRequest.source().query(QueryBuilders.matchAllQuery());

        searchRequest.source().sort("price", SortOrder.DESC);
        searchRequest.source().from(0).size(5);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //抽取解析的方法
        handleResponse(response);
    }


    //高亮查询
    @Test
    public void highLight() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel");
        searchRequest.source().query(QueryBuilders.matchQuery("brand", "如家"));
        searchRequest.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //抽取解析的方法
        handleResponse(response);
    }

    //聚合查询
    @Test
    public void aggregation() throws IOException {
        SearchRequest searchRequest = new SearchRequest("hotel");

        searchRequest.source().size(0);
        searchRequest.source().aggregation(AggregationBuilders
                .terms("brandAgg")
                .field("brand")
                .size(10)
        );

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //解析聚合的结果
        Aggregations aggregations = response.getAggregations();
        Terms brandTerms = aggregations.get("brandAgg");
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            System.out.println(key);
        }
    }


    //自动补全查询
    @Test
    public void completeSearch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions", SuggestBuilders
                        .completionSuggestion("suggestion")
                        .prefix("sd")
                        .skipDuplicates(true)
                        .size(20)
        ));

        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestion = suggest.getSuggestion("suggestions");

        for (CompletionSuggestion.Entry.Option option : suggestion.getOptions()) {
            String text = option.getText().string();
            System.out.println(text);
        }
    }


    private static void handleResponse(SearchResponse response) {
        //解析响应
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;//总条数
        System.out.println("搜索到" + total + "条数据");
        //获取符合的数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);

            //处理高亮部分
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (!CollectionUtils.isEmpty(highlightFields)) {
                HighlightField highlightField = highlightFields.get("name");
                if (highlightField != null) {
                    String name = highlightField.getFragments()[0].string();
                    hotelDoc.setName(name);
                }
            }
            System.out.println(hotelDoc);
        }
    }


}
