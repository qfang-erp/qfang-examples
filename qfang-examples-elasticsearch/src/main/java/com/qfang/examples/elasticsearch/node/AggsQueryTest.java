package com.qfang.examples.elasticsearch.node;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentileRanksBuilder;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesBuilder;
import org.elasticsearch.search.aggregations.metrics.stats.extended.ExtendedStatsBuilder;

public class AggsQueryTest {

	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub

		
		Client client = TransportClient.builder().build()
		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("169.254.135.217"), 9300));
		//avgQuery(client);
		//minQuery(client);
		//maxQuery(client);
		//valueCountQuery(client);
		//extendedStatsQuery(client);
		//percentileQuery(client);
		//percentileRankQuery(client);
		//rangeQuery(client);
		//histogramQuery(client);
		//dateHistogramQuery(client);
		client.close();
	}
	
	public static void avgQuery(Client client ) {
		SearchResponse res = null;
		AvgBuilder agg = AggregationBuilders
				.avg("avg_num")
				.field("like");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	public static void minQuery(Client client ) {
		SearchResponse res = null;
		MinBuilder agg = AggregationBuilders
				.min("min_num")
				.field("like");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}

	public static void maxQuery(Client client ) {
		SearchResponse res = null;
		MaxBuilder agg = AggregationBuilders
				.max("max_num")
				.field("like");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	public static void extendedStatsQuery(Client client ) {
		SearchResponse res = null;
		ExtendedStatsBuilder agg = AggregationBuilders
				.extendedStats("extended_stats_num")
				.field("like");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}

	@SuppressWarnings("rawtypes")
	public static void valueCountQuery(Client client ) {
		SearchResponse res = null;
		
		MetricsAggregationBuilder agg =
		        AggregationBuilders
		                .count("agg")
		                .field("like");
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	public static void percentileQuery(Client client ) {
		SearchResponse res = null;
		PercentilesBuilder agg = AggregationBuilders
				.percentiles("percentile_num")
				.field("like")
				.percentiles(95,99,99.9);
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}

	public static void percentileRankQuery(Client client ) {
		SearchResponse res = null;
		PercentileRanksBuilder agg = AggregationBuilders
				.percentileRanks("percentile_rank_num")
				.field("like")
				.percentiles(3,5);
		
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(10)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	@SuppressWarnings("rawtypes")
	public static void rangeQuery(Client client ) {
		SearchResponse res = null;
		
		AggregationBuilder agg =
		        AggregationBuilders
		                .range("agg")
		                .field("like")
		                .addUnboundedTo(3)
		                .addRange(3, 5)
		                .addUnboundedFrom(5);  
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(0)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	@SuppressWarnings("rawtypes")
	public static void histogramQuery(Client client ) {
		SearchResponse res = null;
		
		AggregationBuilder agg =
		        AggregationBuilders
		                .histogram("agg")
		                .field("like")
		                .interval(2);
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(0)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
	
	@SuppressWarnings("rawtypes")
	public static void dateHistogramQuery(Client client ) {
		SearchResponse res = null;
		
		AggregationBuilder agg =
		        AggregationBuilders
		                .dateHistogram("agg")
		                .field("publish_date")
		                .interval(DateHistogramInterval.YEAR)
		                .minDocCount(1);
		res = client.prepareSearch("search_test")
				.setTypes("article")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.addAggregation(agg)
				.setFrom(0)
				.setSize(0)
				.execute().actionGet();
		System.out.println(res);
		
		// on shutdown
		client.close();
	}
}
