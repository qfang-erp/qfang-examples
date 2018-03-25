package com.qfang.examples.pattern.interpreter;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-25
 * @since: 1.0
 */
public abstract class SymbolNode implements Node {

    protected final Node left;
    protected final Node right;

    protected SymbolNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

}
