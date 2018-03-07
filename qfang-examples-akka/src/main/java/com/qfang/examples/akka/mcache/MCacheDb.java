package com.qfang.examples.akka.mcache;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.qfang.examples.akka.mcache.message.GetRequest;
import com.qfang.examples.akka.mcache.message.KeyNotFoundException;
import com.qfang.examples.akka.mcache.message.SetRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class MCacheDb extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    private final Map<String, String> map = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SetRequest.class, message -> {
                    log.info("Received Set request: {}", message);
                    map.put(message.getKey(), message.getValue());
                })
                .match(GetRequest.class, message -> {
                    log.info("Received Get request: {}", message);
                    String value = map.get(message.getKey());
                    Object response = StringUtils.isNoneEmpty(value) ? value : new Status.Failure(new KeyNotFoundException(message.getKey()));
                    sender().tell(response, self());
                })
                .matchAny(o ->
                    sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                )
                .build();
    }

    public String getValue(String key) {
        return this.map.get(key);
    }

}
