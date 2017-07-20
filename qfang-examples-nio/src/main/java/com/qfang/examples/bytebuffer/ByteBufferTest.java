package com.qfang.examples.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by walle on 2017/3/12.
 */
public class ByteBufferTest {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        byte[] ba = new byte[] {0, 1, 2, 3};
        buffer.put(ba);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        buffer.flip();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        byte[] ba2 = new byte[2];
        buffer.get(ba2);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());
        System.out.println(Arrays.toString(ba2));

        buffer.rewind();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        buffer.compact();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        buffer.clear();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        buffer.position(2);
        buffer.get(ba2);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());
        System.out.println(Arrays.toString(ba2));
    }
}
