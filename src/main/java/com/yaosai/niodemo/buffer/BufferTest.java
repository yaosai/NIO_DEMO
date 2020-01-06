package com.yaosai.niodemo.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 1.缓冲区是NIO通信时，数据的载体。
 * 2.开发中，常用的缓冲区是ByteBuffer(字节缓冲区)
 * 3.缓冲区的属性：capacity(容量),决定了存储容量的上限。一经写定，不能更改
 * 4.缓冲区的属性：limit(限制),限制的初始位置=capacity
 * 5.缓冲区的属性：position(位置),初始位置是0
 * 6.ByteBuffer默认用的子类：HeapByteBuffer(堆内字节缓冲区),这种类型
 * 的缓冲区，在JVM的堆中创建的，即此缓冲区的生命周期(GC)由JVM管理的
 * 7.MappedByteBuffer(堆外字节缓冲区),可以使用操作系统的内存，使用场景
 * 当创建大的字节缓冲区时，注意：如果使用堆外，生命周期的管理需要自己来实现
 * 8.position位置，每插入一个字节，就会向后移动一位
 * 9.get()方法会根据当前position的位置取值,
 * 此外，get()每调用一次，位置也会移动一位
 *
 * @author YaoS
 * @date 19/3/11 17:45
 */
public class BufferTest {

    @Test
    public void create() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("");
    }

    @Test
    public void put() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte b1 = 1;
        byte b2 = 2;

        buffer.put(b1);
        buffer.put(b2);
        buffer.put("hello".getBytes());
        System.out.println("");
    }

    @Test
    public void get() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte b1 = 1;
        byte b2 = 2;
        byte b3 = 3;
        buffer.put(b1);
        buffer.put(b2);
        buffer.put(b3);

        // 将limit置到当前的position
        buffer.limit(buffer.position());

        // 将position置到初始位置
        buffer.position(0);
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());

    }

    @Test
    public void flip() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte b1 = 1;
        byte b2 = 2;
        byte b3 = 3;
        buffer.put(b1);
        buffer.put(b2);
        buffer.put(b3);
        // flip 反转缓冲区，切换到缓冲区的读模式
        // 等价于buffer.limit(buffer.position())+buffer.position(0);
        buffer.flip();
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
    }

    @Test
    public void wrap() {
        // 会根据传入的字节数组大小创建对应的大小缓冲区，并写入数据
        // 此外，写完数据后，自动调用flip()
        ByteBuffer buffer = ByteBuffer.wrap("helloworld".getBytes());
        System.out.println("");
    }

    @Test
    public void clear() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte b1 = 1;
        byte b2 = 2;
        byte b3 = 3;
        buffer.put(b1);
        buffer.put(b2);
        buffer.put(b3);
        // clear方法并不能真正清除缓冲区数据，只是将position置为0
        buffer.clear();
        byte b4 = 4;
        byte b5 = 5;
        // clear之后，再写数据，为了避免读到历史数据，一定别忘了调用flip
        buffer.put(b4);
        buffer.put(b5);
        buffer.flip();
    }

    @Test
    public void hasRemaining() {
        ByteBuffer buffer = ByteBuffer.wrap("helloworld".getBytes());

        // hasRemaining()方法会判断 limit和position之间是否还有元素可读
        // 如果有就返回true,如果没有，就返回false,跳出循环
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
