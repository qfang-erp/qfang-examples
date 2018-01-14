package com.qfang.examples.akka.mcache.remote.client;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.qfang.examples.akka.mcache.message.GetRequest;
import com.qfang.examples.akka.mcache.message.SetRequest;

import java.util.concurrent.CompletionStage;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2017-11-04
 * @since: 1.0
 */
public class JClient {

    private final ActorSystem system = ActorSystem.create("akkademy");
    private final ActorSelection remoteDb;

    public JClient(String remoteAddress) {
        remoteDb = system.actorSelection("akka.tcp://akkademy@" + remoteAddress + "/user/mCacheDb");
    }

    public CompletionStage set(String key, String value) {
        return toJava(ask(remoteDb, new SetRequest(key, value), 2000));
    }

    public CompletionStage<Object> get(String key) {
        return toJava(ask(remoteDb, new GetRequest(key), 2000));
    }

}
