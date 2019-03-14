package com.yaosai.niodemo.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器服务启动器
 *
 * @author YaoS
 * @date 19/3/11 17:57
 */
public class ServerStart {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9000));
        // 获取多路复用选择器
        Selector selector = Selector.open();
        // 在服务端通道上绑定监听器，并监听接入事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 此方法是一个阻塞方法，阻塞放开的条件是有事件发生
            selector.select();
            // 如果代码走到这，说明阻塞放开，说明有事件发生，即需要处理事件
            Set<SelectionKey> set = selector.selectedKeys();
            // 获取键集(事件集合)的迭代器
            Iterator<SelectionKey> it = set.iterator();

            while (it.hasNext()) {
                // 获取一个事件
                SelectionKey sk = it.next();
                // 表示有客户端接入事件
                if (sk.isAcceptable()) {
                    ServerSocketChannel server =
                            (ServerSocketChannel) sk.channel();
                    // 建立和对应客户端通信的通道
                    SocketChannel sc = server.accept();
                    // 设置非阻塞模式
                    sc.configureBlocking(false);
                    //  OP_READ  0000 0001
                    //  OP_WRITE 0000 0100
                    //           0000 0101
                    System.out.println("接入客户端,处理线程号:" + Thread.currentThread().getId());
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                }
                // 表示客户端有数据发给服务端，我们服务端要读数据
                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    sc.read(buffer);
                    System.out.println("收到客户端数据:" + new String(buffer.array()) + "线程编号:" + Thread.currentThread().getId());

                }
                // 表示客户端准备好接受数据，我们服务要写出数据
                if (sk.isWritable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.wrap("1234".getBytes());
                    // 因为write和read是非阻塞方法
                    // 所以为了确保读或写数据完整，需要加上hasRemaining()
                    while (buffer.hasRemaining()) {
                        sc.write(buffer);
                    }

                }
                // 事件处理完后，移除此事件，避免重复处理
                it.remove();
            }

        }
    }
}
