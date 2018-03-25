package com.qfang.examples.pattern.chainOfResponsibility;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class InfoMessagePrintHandler implements MessagePrintHandler {

    @Override
    public boolean canHandle(Message message) {
        return message.getLevel() == Message.INFO;
    }

    @Override
    public void handle(Message message) {
        System.out.println("info: " + message.getContent());
    }
}
