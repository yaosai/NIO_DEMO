package com.yaosai.biodemo.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author YaoS
 * @date 2019-03-11 17:44
 */
class ClientRunner implements Runnable{
	private Socket socket;

	public ClientRunner(Socket socket) {
		this.socket=socket;
	}

	@Override
	public void run() {
		try {
			InputStream in=socket.getInputStream();
			byte[] data=new byte[10];
			in.read(data);
			System.out.println("服务端收到数据:"+new String(data)+"线程编号:"+Thread.currentThread().getId());

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
