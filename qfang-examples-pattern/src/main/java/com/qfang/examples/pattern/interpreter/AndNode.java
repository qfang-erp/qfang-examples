package com.qfang.examples.pattern.interpreter;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-25
 * @since: 1.0
 */
public class AndNode extends SymbolNode {

    protected AndNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public boolean interpreter() {
        return left.interpreter() & right.interpreter();
    }
}
