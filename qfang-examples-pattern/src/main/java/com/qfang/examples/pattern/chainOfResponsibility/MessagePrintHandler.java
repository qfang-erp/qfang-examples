package com.qfang.examples.pattern.chainOfResponsibility;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public interface MessagePrintHandler {

    boolean canHandle(Message message);

    void handle(Message message);

}
