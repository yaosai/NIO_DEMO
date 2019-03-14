package com.yaosai.niodemo.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 客户端多路复用器启动代码
 *
 * @author YaoS
 * @date 19/3/11 16:34
 */
public class ClientSelectorStart {

    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9000));
        Selector selector = Selector.open();
        // 客户端注册选择器，并监听连接事件
        sc.register(selector, SelectionKey.OP_CONNECT);

        while (true) {
            selector.select();
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> it = set.iterator();

            while (it.hasNext()) {
                SelectionKey sk = it.next();
                // 客户端成功连接服务端
                if (sk.isConnectable()) {
                    SocketChannel client = (SocketChannel) sk.channel();
                    client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                }
                // 服务端发数据，客户端读数据
                if (sk.isReadable()) {
                    SocketChannel client = (SocketChannel) sk.channel();
                    ByteBuffer data = ByteBuffer.allocate(10);
                    while (data.hasRemaining()) {
                        client.read(data);
                    }
                }
                // 客户端要给服务发数据，而且服务端已准备好
                if (sk.isWritable()) {

                }
                it.remove();
            }

        }
    }

}
