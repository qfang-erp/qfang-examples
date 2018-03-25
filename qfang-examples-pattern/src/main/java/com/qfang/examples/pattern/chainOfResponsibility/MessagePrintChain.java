package com.qfang.examples.pattern.chainOfResponsibility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class MessagePrintChain {

    private List<MessagePrintHandler> handlers = new ArrayList<>();

    public MessagePrintChain addHandler(MessagePrintHandler handler) {
        handlers.add(handler);
        return this;
    }

    public void handle(Message message) {
        Iterator<MessagePrintHandler> it = handlers.iterator();
        while (it.hasNext()) {
            MessagePrintHandler h = it.next();
            if(h.canHandle(message))
                h.handle(message);
        }
    }

}
