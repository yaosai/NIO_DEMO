package com.yaosai.biodemo.pool;

import java.net.Socket;

/**
 * 客户端启动代码
 *
 * @author YaoS
 * @date 19/3/11 16:27
 */
public class ClientStart {

	public static void main(String[] args) throws Exception {
		for(int i=0;i<20;i++){
			Socket socket=new Socket("127.0.0.1",9000);
		}
	}
}
