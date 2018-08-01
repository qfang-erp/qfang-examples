package com.qfang.examples.elasticsearch.node;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author huxianyong
 * @date 2018/3/23
 * @since 1.0
 */
public class ClientFactory {

    public static Client createClient(){
        Client client = null;
        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", "qfang-log-6").build();
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("10.251.93.252"), 9306));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
