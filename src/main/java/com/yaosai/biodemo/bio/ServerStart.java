package com.yaosai.biodemo.bio;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author YaoS
 * @date 19/3/11 16:25
 */
public class ServerStart {

	public static void main(String[] args) throws Exception {
		ServerSocket server=new ServerSocket(9000);

		while(true){
			Socket socket=server.accept();
			new Thread(new ClientRunner(socket)).start();
		}
	}
}
