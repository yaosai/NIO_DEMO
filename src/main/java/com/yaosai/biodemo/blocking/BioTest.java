package com.yaosai.biodemo.blocking;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO阻塞测试
 *
 * @author YaoS
 * @date 19/3/11 17:43
 */
public class BioTest {

    /**
     * accept此方法会产生阻塞，直到有一个客户端接入，阻塞放开
     * read()方法也会产生阻塞，阻塞放开的条件是有数据可读
     * write()方法也会产生阻塞，当一方写，但另一方不读，写出到一定量时，就会产生阻塞
     */
    @Test
    public void testAcceptReadWrite() throws IOException {
        ServerSocket server = new ServerSocket(9000);
        Socket socket = server.accept();

        InputStream in = socket.getInputStream();
        /**
         * 服务端和客户端连接之后，读数据，但是客户端并不发数据
         */
//		in.read();
//		System.out.println("hello");
        OutputStream out = socket.getOutputStream();
        for (int i = 0; i < 100000; i++) {
            out.write("helloWorld".getBytes());
            System.out.println(i);
        }
    }

    /**
     * 功能描述: 测试Connect方法
     * connect方法也会产生阻塞，阻塞放开的条件是真正建立连接或有异常抛出
     *
     * @author YaoS
     * @date 19/3/11 17:25
     */
    @Test
    public void testConnect() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9000));
        /**
         * 保持客户端连接一直开启
         */
        while (true) {

        }

    }
}