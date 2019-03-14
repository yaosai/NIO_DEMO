package com.yaosai.biodemo.pool;

import java.net.Socket;

/**
 * 重写run方法
 *
 * @author YaoS
 * @date 2019-03-11 16:32
 */
class PoolRunner implements Runnable {
    private Socket socket;

    public PoolRunner(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("处理当前请求的线程编号:" + Thread.currentThread().getId());

    }

}
