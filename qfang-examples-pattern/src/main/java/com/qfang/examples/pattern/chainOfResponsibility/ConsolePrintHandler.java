package com.qfang.examples.pattern.chainOfResponsibility;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class ConsolePrintHandler implements MessagePrintHandler {
    @Override
    public boolean canHandle(Message message) {
        return true;
    }

    @Override
    public void handle(Message message) {
        System.out.println("console: " + message.getContent());
    }
}
