package com.yaosai.niodemo.channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端启动类
 *
 * @author YaoS
 * @date 19/3/11 17:46
 */
public class ClientStart {

    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open();

        sc.connect(new InetSocketAddress("127.0.0.1", 9000));

        ByteBuffer buffer = ByteBuffer.wrap("helloWorld".getBytes());
        sc.write(buffer);

        while (true) {

        }

    }
}
