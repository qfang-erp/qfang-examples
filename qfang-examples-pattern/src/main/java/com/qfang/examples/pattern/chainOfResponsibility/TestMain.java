package com.qfang.examples.pattern.chainOfResponsibility;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-10
 * @since: 1.0
 */
public class TestMain {

    public static void main(String[] args) {
        MessagePrintChain chain = new MessagePrintChain();
        chain.addHandler(new InfoMessagePrintHandler())
                .addHandler(new DebugMessagePrintHandler())
                .addHandler(new ConsolePrintHandler());

        Message m1 = new Message(Message.INFO, " test info ");
        chain.handle(m1);

        System.out.println("------------------");

        Message m2 = new Message(Message.DEBUG, " test debug ");
        chain.handle(m2);
    }

}
