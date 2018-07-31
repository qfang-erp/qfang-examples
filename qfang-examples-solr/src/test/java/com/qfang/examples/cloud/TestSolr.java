package com.qfang.examples.cloud;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.UUID;

/**
 * @author huxianyong
 * @date 2017/7/20
 * @since 1.0
 */
public class TestSolr {

    public static void main(String[] args) {
        SolrConfig solrConfig=new SolrConfig();
        solrConfig.setZkHost("192.168.0.40:2181/erpsolr");
        solrConfig.setDefaultCollection("tradeSearch");
        solrConfig.setZkClientTimeout(30000);
        solrConfig.setZkConnectTimeout(30000);

        SolrTradeSearch solrTradeSearch=new SolrTradeSearch();
        solrTradeSearch.setSolrConfig(solrConfig);

        TradeSearch entity=new TradeSearch();
        entity.setId(UUID.randomUUID().toString());
        entity.setCity("SHEZHEN");
        entity.setNumber("123");
        entity.setTenementDetail("123");
        entity.setType("123");
        solrTradeSearch.save(entity,false);
        System.out.println("保存成功！");
        SolrQuery solrQuery=new SolrQuery();
        solrQuery.setQuery("*:*");
        Pagination<TradeSearch> pagination= solrTradeSearch.query(solrQuery,new Pagination<TradeSearch>(20,1));
        pagination.getItems().forEach(System.out::println);




    }
}
