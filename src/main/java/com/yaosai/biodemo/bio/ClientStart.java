package com.yaosai.biodemo.bio;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 客户端代码
 *
 * @author YaoS
 * @date 19/3/11 16:25
 */
public class ClientStart {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 9000));
        OutputStream out = socket.getOutputStream();

        out.write("helloWorld".getBytes());
        //保持客户端线程一直开启
        while (true) {

        }
    }
}
