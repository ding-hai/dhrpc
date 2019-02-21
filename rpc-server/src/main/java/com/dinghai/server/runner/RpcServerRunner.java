package com.dinghai.server.runner;

import com.dinghai.server.net.SocketAcceptor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RpcServerRunner {
    private static ExecutorService executor;

    public void run() {
        executor = Executors.newFixedThreadPool(1);
        //接受请求
        executor.execute(new SocketAcceptor());
        //注册服务提供者的信息

    }

    @PreDestroy
    public void destroy() {
        if (null != executor) {
            executor.shutdown();
        }
    }
}
