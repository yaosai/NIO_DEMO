package com.yaosai.niodemo.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端启动代码
 *
 * @author YaoS
 * @date 19/3/11 16:33
 */
public class ClientStart {

    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9000));

        /**
         * 这样处理是为了避免非阻塞连接带来的空指针异常的问题
         */
        while (!sc.isConnected()) {
            sc.finishConnect();
        }

        ByteBuffer buffer = ByteBuffer.wrap("helloWorld".getBytes());
        sc.write(buffer);

        ByteBuffer data = ByteBuffer.allocate(4);

        while (data.hasRemaining()) {
            sc.read(data);
        }

        System.out.println("客户端收到数据:" + new String(data.array()));

        System.out.println("success");
        /**
         * 保持客户端线程一直开启
         */
        while (true) {

        }

    }

}
