package com.qfang.examples.pattern.visitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.List;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-03-18
 * @since: 1.0
 */
public class TestMain {


    public static void main(String[] args) throws IOException {
        ClassReader classReader = new ClassReader(Person.class.getName());
        System.out.println(classReader.getClassName());

        System.out.println("======= method ==========");

        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
        List<MethodNode> methods = classNode.methods;
        for(MethodNode methodNode : methods) {
            System.out.println(methodNode.name);
        }

        System.out.println("======= field ==========");

        List<FieldNode> fields = classNode.fields;
        for(FieldNode fieldNode : fields) {
            System.out.println(fieldNode.name);
        }
    }

}
