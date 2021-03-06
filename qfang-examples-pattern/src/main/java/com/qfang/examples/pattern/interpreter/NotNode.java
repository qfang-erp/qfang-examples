package com.qfang.examples.pattern.interpreter;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-25
 * @since: 1.0
 */
public class NotNode extends ValueNode {

    public NotNode(boolean value) {
        super(value);
    }

    @Override
    public boolean interpreter() {
        return !this.value;
    }
}
