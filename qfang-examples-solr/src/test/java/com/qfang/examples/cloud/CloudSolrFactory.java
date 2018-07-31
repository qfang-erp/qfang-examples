package com.qfang.examples.cloud;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huxianyong
 * @date 2017/7/20
 * @since 1.0
 */
public class CloudSolrFactory {
    private static final Logger logger=Logger.getLogger(CloudSolrFactory.class);

    private static Map<String,CloudSolrClient> solrClientMap= Collections.synchronizedMap(new HashMap<String,CloudSolrClient>());

    public static CloudSolrClient  getCloudSolrClient(SolrConfig solrConfig){
        CloudSolrClient solrClient=null;
        String key  =solrConfig.getKey();
        if(solrClientMap.containsKey(key)){
            solrClient=solrClientMap.get(key);
            try {
                if (solrClient != null && solrClient.getZkStateReader().getZkClient().isConnected()) {
                    return solrClient;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        solrClient=new  CloudSolrClient.Builder().withZkHost(solrConfig.getZkHost()).build();
        solrClient.setDefaultCollection(solrConfig.getDefaultCollection());
        solrClient.setZkClientTimeout(solrConfig.getZkClientTimeout());
        solrClient.setZkConnectTimeout(solrConfig.getZkConnectTimeout());
        solrClientMap.put(key,solrClient);
        return solrClient;
    }
}
