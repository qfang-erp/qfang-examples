package com.qfang.examples.spring.ch7.dynamicproxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: liaozhicheng.cn@163.com
 * @date: 2018-02-22
 * @since: 1.0
 */
public class ProxyGeneratorUtils {

    public static void writeProxyClassToHardDisk(String path, Class<?> proxyClass) {
        // 第一种方法，这种方式在刚才分析 ProxyGenerator 时已经知道了（暂时没有找到文件写到了哪个目录）
        // System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);

        // 第二种方法
        // 获取代理类的字节码
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11", proxyClass.getInterfaces());
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
