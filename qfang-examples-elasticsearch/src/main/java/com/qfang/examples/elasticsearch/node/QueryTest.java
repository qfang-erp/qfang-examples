package com.qfang.examples.elasticsearch.node;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;

public class QueryTest {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
//		Client client = TransportClient.builder().build()
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.110"), 9300));
		
		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("169.254.135.217"), 9300));
		//matchAllQuery(client);
		/* full text queries */
		//matchQuery(client);
		//multiMatchQuery(client);
		//commonTermQuery(client);
		/* end of full text queries */
		/* term query */
		//rangeQuery(client);
		//termQuery(client);
		othersQuery(client);
		/* end of term query*/
		client.close();
	}
	
	public static void matchAllQuery(Client client ) {
		SearchResponse res = null;
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()){
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
	}
	
	public static void matchQuery(Client client ) {
		SearchResponse res = null;
		QueryBuilder qb = QueryBuilders.matchQuery("title", "article");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()){
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
	}
	
	public static void multiMatchQuery(Client client ) {
		SearchResponse res = null;
		QueryBuilder qb = QueryBuilders
				.multiMatchQuery("article","title", "body");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()) { 
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
	}
	
	public static void commonTermQuery(Client client ) {
		SearchResponse res = null;
		QueryBuilder qb = QueryBuilders
				.commonTermsQuery("title","article");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
		
		//common terms query
	}
	
	public static void termQuery(Client client ) {
		SearchResponse res = null;
		QueryBuilder qb = QueryBuilders
				.termQuery("title","article");
		
//		QueryBuilder qb = QueryBuilders
//				.termsQuery("title","article","relevence");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.addHighlightedField("title")
				.setHighlighterPreTags("<span class='searchKey'>")
				.setHighlighterPostTags("</san>")
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits()) {
			Map<String, HighlightField> result = hit.highlightFields();
			HighlightField highlightedSummary = result.get("title");
			Text[] titleTexts = highlightedSummary.getFragments();
			String allFragments = "";
			for (Text text: titleTexts){
				allFragments += text;
			}
			System.out.println(result);
			System.out.println(allFragments);		
		}
		
		// on shutdown
		client.close();
		
		//common terms query
	}
	
	public static void rangeQuery(Client client ) {
		SearchResponse res = null;
		
		QueryBuilder qb = QueryBuilders
				.rangeQuery("like")
				.gte(5)
				.lt(7);
		
		
//		QueryBuilder qb = QueryBuilders
//				.rangeQuery("like")
//				.from(5)
//				.to(7)
//				.includeLower(true)
//				.includeUpper(false);
		
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
		
		//common terms query
	}
	
	public static void othersQuery(Client client ) {
		SearchResponse res = null;
		
		QueryBuilder qb = QueryBuilders.existsQuery("str");
		//QueryBuilder qb = QueryBuilders.prefixQuery("name", "prefix");
		//QueryBuilder qb = QueryBuilders.wildcardQuery("user", "k?mc*");
		//QueryBuilder qb = QueryBuilders.regexpQuery("user", "k.*y");
		//QueryBuilder qb = QueryBuilders.fuzzyQuery("name", "kimzhy");
		//QueryBuilder qb = QueryBuilders.typeQuery("my_type");
		//QueryBuilder qb = QueryBuilders.idsQuery("my_type","type2").addIds("1","2","5");

		
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(qb)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		for (SearchHit hit: res.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		
		// on shutdown
		client.close();
		
		//common terms query
	}

}
