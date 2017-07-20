package com.qfang.examples.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年4月14日
 * @since 1.0
 */
public class IndexTests extends BaseClientTests {
	
	@Test
	public void indexGetTest() {
		// 获取索引
		SearchResponse response = client.prepareSearch(INDEX_NAME).setTypes(TYPE_NAME).get();
		System.out.println(response);
	}
	
	@Test
	public void indexCreateTest() throws IOException {
		// 创建索引，并插入数据
		IndexResponse response = client.prepareIndex("index01", "newType", "1")
			  		.setSource(jsonBuilder()
							.startObject()
								.field("user", "kimchy")
								.field("postDate", new Date())
								.field("message", "trying out Elasticsearch")
							.endObject()
							)
				.get();
		System.out.println(response);
	}
	
	@Test
	public void indexDeleteTest() throws InterruptedException, ExecutionException {
		// 删除索引
		DeleteIndexResponse response = client.admin().indices().delete(new DeleteIndexRequest("index01")).get();
		System.out.println(response.isAcknowledged());
	}
	
}
