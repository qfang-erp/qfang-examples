package com.qfang.examples.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.gson.Gson;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月15日
 * @since 1.0
 */
public class DocumentTests extends BaseClientTests {
	
	@Test
	public void insertDocumentTest() {
		// 插入数据
		Map<String, Object> customer = Maps.newHashMap();
		customer.put("name", "test");
		customer.put("age", 40);
		customer.put("state", "close");
		customer.put("gender", "man");
		customer.put("balance", 100);
		String json = new Gson().toJson(customer);
		
		IndexResponse response = client.prepareIndex(INDEX_NAME, TYPE_NAME).setSource(json)
				.execute().actionGet();
		System.out.println(response);
	}
	
	@Test
	public void deleteDocumentTest() {
		String id = "AVQcV6ZuJfzEoA_96tQB";
		DeleteResponse response = client.prepareDelete(INDEX_NAME, TYPE_NAME, id).execute()
				.actionGet();
		System.out.println(response.isFound());
	}
	
	@Test
	public void updateDocumentTest1() throws IOException {
		UpdateResponse response = client.prepareUpdate(INDEX_NAME, TYPE_NAME, "AVQcXWKy50MnK8WFflRS")
										.setDoc(
											jsonBuilder()
											.startObject()
											.field("name", "testUpdate")
											.field("state", "open")
											.endObject()
										).get();
		System.out.println(response);
	}
	
	@Test
	public void updateDocumentTest2() throws IOException, InterruptedException, ExecutionException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(INDEX_NAME).type(TYPE_NAME).id("AVQcXWKy50MnK8WFflRS");
		updateRequest.doc(jsonBuilder()
							.startObject()
							.field("balance", 500)
							.endObject()
						);
		UpdateResponse response = client.update(updateRequest).get();
		System.out.println(response);
	}
	
	@Test
	public void updateWithBulkTest() throws IOException {
		BulkRequestBuilder request = client.prepareBulk();
		
		request.add(client.prepareUpdate(INDEX_NAME, TYPE_NAME, "AVQcXWKy50MnK8WFflRS")
							.setDoc(jsonBuilder()
									.startObject()
									.field("name", "testUpdateBulk")
									.endObject()
							)
					);
		request.add(client.prepareUpdate(INDEX_NAME, TYPE_NAME, "AVQcU5cD50MnK8WFflRQ")
							.setDoc(jsonBuilder()
									.startObject()
									.field("name", "testUpdateBulk2")
									.endObject()
							)
					);
		BulkResponse response = request.execute().actionGet();
		Assert.assertFalse(response.hasFailures());
	}
	
	@Test
	public void matchAllQuery() {
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
		SearchResponse response = client.prepareSearch(INDEX_NAME)
										.setTypes(TYPE_NAME)
										.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
										.setQuery(queryBuilder)
										.setFrom(0)
										.setSize(10)
										.execute()
										.actionGet();
		for (SearchHit hit : response.getHits().getHits()) {
			System.out.println(hit.getSourceAsString());
		}
	}
	
}
