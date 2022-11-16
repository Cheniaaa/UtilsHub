package cn.es.service;

import cn.es.dto.HotelDoc;
import cn.es.dto.PageResult;
import cn.es.param.HotelParam;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.es.entity.TbHotel;
import cn.es.mapper.TbHotelMapper;

@Service
public class TbHotelService extends ServiceImpl<TbHotelMapper, TbHotel> {


    @Autowired
    RestHighLevelClient restHighLevelClient;


    public PageResult search(HotelParam param) {
        try {
            SearchRequest request = new SearchRequest("hotel");

            //查询条件
            boolQuery(param, request);

            //分页
            int page = param.getPage();
            int size = param.getSize();
            request.source().from((page - 1) * size).size(size);

            //距离排序
            String location = param.getLocation();
            if (!location.isBlank()) {
                request.source().sort(SortBuilders
                        .geoDistanceSort("location", new GeoPoint(location))
                        .order(SortOrder.ASC)
                        .unit(DistanceUnit.KILOMETERS));
            }
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

            return handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void boolQuery(HotelParam param, SearchRequest request) {
        // 1.准备Boolean查询
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 1.1.关键字搜索，match查询，放到must中
        String key = param.getKey();
        if (StringUtils.isNotBlank(key)) {
            boolQuery.must(QueryBuilders.matchQuery("all", key));
        } else {
            boolQuery.must(QueryBuilders.matchAllQuery());
        }

        // 1.2.品牌
        String brand = param.getBrand();
        if (StringUtils.isNotBlank(brand)) {
            boolQuery.filter(QueryBuilders.termQuery("brand", brand));
        }
        // 1.3.城市
        String city = param.getCity();
        if (StringUtils.isNotBlank(city)) {
            boolQuery.filter(QueryBuilders.termQuery("city", city));
        }
        // 1.4.星级
        String starName = param.getStarName();
        if (StringUtils.isNotBlank(starName)) {
            boolQuery.filter(QueryBuilders.termQuery("starName", starName));
        }
        // 1.5.价格范围
        Integer minPrice = param.getMinPrice();
        Integer maxPrice = param.getMaxPrice();
        if (minPrice != null && maxPrice != null) {
            maxPrice = maxPrice == 0 ? Integer.MAX_VALUE : maxPrice;
            boolQuery.filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice));
        }

        //指定算法策略，默认为乘
        FunctionScoreQueryBuilder scoreQuery = QueryBuilders.functionScoreQuery(boolQuery, new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                new FunctionScoreQueryBuilder
                        .FilterFunctionBuilder(
                        QueryBuilders.termQuery("brand", "如家"),
                        ScoreFunctionBuilders.weightFactorFunction(10)//增加权重
                )
        });
        request.source().query(scoreQuery);
    }


    private static PageResult handleResponse(SearchResponse response) {
        //解析响应
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;//总条数
        System.out.println("搜索到" + total + "条数据");
        List<HotelDoc> hotelDocList = new ArrayList<>();
        //获取符合的数据
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);

            Object[] sortValues = hit.getSortValues();
            if (sortValues.length > 0) {
                hotelDoc.setDistance(sortValues[0]);
            }
            hotelDocList.add(hotelDoc);

        }
        return new PageResult(total, hotelDocList);
    }

    public Map<String, List<String>> filter(HotelParam param) {
        try {
            SearchRequest searchRequest = new SearchRequest("hotel");

            //查询
            boolQuery(param, searchRequest);

            //聚合
            buildAggregation(searchRequest);

            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            HashMap<String, List<String>> res = new HashMap<>();
            //解析聚合的结果
            Aggregations aggregations = response.getAggregations();

            List<String> brandAggList = getAggByName(aggregations, "brandAgg");
            res.put("品牌", brandAggList);
            List<String> cityAggList = getAggByName(aggregations, "cityAgg");
            res.put("城市", cityAggList);
            List<String> starAggList = getAggByName(aggregations, "starAgg");
            res.put("星级", starAggList);

            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getAggByName(Aggregations aggregations, String aggName) {
        Terms brandTerms = aggregations.get(aggName);
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        List<String> resList = new ArrayList<>();

        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            resList.add(key);
        }
        return resList;
    }

    private static void buildAggregation(SearchRequest searchRequest) {
        searchRequest.source().size(0);
        searchRequest.source().aggregation(AggregationBuilders
                .terms("brandAgg")
                .field("brand")
                .size(100)
        );
        searchRequest.source().aggregation(AggregationBuilders
                .terms("cityAgg")
                .field("city")
                .size(100)
        );
        searchRequest.source().aggregation(AggregationBuilders
                .terms("starAgg")
                .field("starName")
                .size(100)
        );
    }

    public List<String> getSuggestions(String prefix) {
        try {
            SearchRequest request = new SearchRequest("hotel");
            request.source().suggest(new SuggestBuilder().addSuggestion(
                    "suggestions", SuggestBuilders
                            .completionSuggestion("suggestion")
                            .prefix(prefix)
                            .skipDuplicates(true)
                            .size(20)
            ));

            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            Suggest suggest = response.getSuggest();
            CompletionSuggestion suggestion = suggest.getSuggestion("suggestions");

//        List<String> res = new ArrayList<>();
//        for (CompletionSuggestion.Entry.Option option : suggestion.getOptions()) {
//            String text = option.getText().string();
//            res.add(text);
//        }
            List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
            return options.stream().map(option -> option.getText().string()).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
