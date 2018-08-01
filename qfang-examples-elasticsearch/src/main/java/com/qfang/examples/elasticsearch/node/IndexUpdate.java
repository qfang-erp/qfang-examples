package com.qfang.examples.elasticsearch.node;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class IndexUpdate {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
//		Client client = TransportClient.builder().build()
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.1.110"), 9300));
		Client client =ClientFactory.createClient();
		//update(client);
		bulkUpdate(client);

	}
	
	public static void update(Client client ) throws IOException, InterruptedException, ExecutionException {
		UpdateRequest req = new UpdateRequest();
		req.index("search_test");
		req.type("article");
		req.id("2");
		req.doc(jsonBuilder().startObject().field("title", "updated title").field("body", "updated body").endObject());
		client.update(req).get();
		client.close();
	}
	
	public static void bulkUpdate(Client client ) throws IOException, InterruptedException, ExecutionException {
		
		BulkRequestBuilder req = client.prepareBulk();
		req.add(client.prepareIndex("search_test","article","2")
					.setSource(jsonBuilder()
							.startObject()
							.field("title","bulk title 02 04-05")));
		req.add(client.prepareIndex("search_test","article","3")
				.setSource(jsonBuilder()
						.startObject()
						.field("title","bulk title 03 04-05")));
		req.add(client.prepareIndex("search_test","article","4")
				.setSource(jsonBuilder()
						.startObject()
						.field("title","bulk title 04 04-05")));
        BulkResponse res = req.execute().actionGet();		
        
        if (res.hasFailures()){
        	System.out.println("Error");
        } else {
        	System.out.println("Done");
        }
        
		
		client.close();
	}
	

	
	

}
