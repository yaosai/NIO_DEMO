package com.yaosai.biodemo.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 服务端启动代码,引入线程池
 *
 * @author YaoS
 * @date 19/3/11 16:27
 */
public class ServerStart {

    public static void main(String[] args) throws Exception {
        /**
         * 手动创建线程池
         */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int size = 10;
        ExecutorService pool = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);

        ServerSocket server = new ServerSocket(9000);
        while (true) {
            Socket socket = server.accept();
            pool.execute(new PoolRunner(socket));
        }
    }

}

