package com.qfang.examples.bytebuffer;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * ByteBuffer 介绍
 *
 * 几个重要属性
 * - mark 默认情况下为 -1
 * - position
 * - limit
 * - capacity
 *
 * 这几个属性满足条件：
 * 0 <= mark <= position <= limit <= capacity
 *
 * 几个重要的方法
 * #put()
 * #get()
 * #flip() 读模式转换 limit -> position; position -> 0
 * #rewind()  读模式下，可以用来重读  position -> 0; limit 不变
 * #compact() & #clear()  写模式转换，区别是一个会保留未读数据，而另一个不会
 * #mark() & #reset()  两个配合使用
 *
 * Created by walle on 2017/3/12.
 */
public class ByteBufferTest {

    public static void main(String[] args) {
        // 默认情况下  position = 0; limit = capacity; capacity = length
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.printf("position: %s \r\n", buffer.position());  // 0
        System.out.printf("limit: %s \r\n", buffer.limit());  // 10
        System.out.printf("capacity: %s \r\n", buffer.capacity());  // 10

        byte[] ba = new byte[] {0, 1, 2, 3};
        buffer.put(ba);  // position++
        System.out.printf("position: %s \r\n", buffer.position());  // 4
        System.out.printf("limit: %s \r\n", buffer.limit());  // 10
        System.out.printf("capacity: %s \r\n", buffer.capacity());  // 10

        // 读转换模式，limit -> position; position -> 0
        buffer.flip();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        // 读取数据， position++
        byte[] ba2 = new byte[2];
        buffer.get(ba2);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());
        System.out.println(Arrays.toString(ba2));

        // position = 0; limit 不变，可以用来在读模式时重读 buff 中的内容
        buffer.rewind();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        // #compact() 与 #clear() 方法的区别，两个模式都是从读模式转换成写模式
        // 区别在于两者一个会保留未读完数据（#compact），另一个不会保留未读完数据（#clear）
        // 将所有未读的数据拷贝到 Buffer 起始处。然后将 position 设到最后一个未读元素后面，limit -> capacity
        buffer.compact();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        // position = 0; limit = capacity，如果此时 buffer 中还有数据未读完，那么未读数据起始索引将会被遗忘
        // buffer 被清空了，但 buffer 中的数据并未清除
        buffer.clear();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());

        // 手动设置 position
        // #mark() & #reset()  #mark 方法可以标记 Buffer 中的一个特定 position，之后可以通过调用 #reset() 恢复到这个 position
        buffer.position(2);
        buffer.mark();
        buffer.get(ba2);
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());
        System.out.println(Arrays.toString(ba2));

        // 恢复到之前 #mark 的位置
        buffer.reset();
        System.out.printf("position: %s \r\n", buffer.position());
        System.out.printf("limit: %s \r\n", buffer.limit());
        System.out.printf("capacity: %s \r\n", buffer.capacity());
    }
}
