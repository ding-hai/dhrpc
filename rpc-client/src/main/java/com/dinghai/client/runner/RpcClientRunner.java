package com.dinghai.client.runner;

import com.dinghai.client.pool.RequestManager;
import com.dinghai.client.pool.ResponsePool;
import com.dinghai.client.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcClientRunner {
    @Autowired
    private ProxyManager proxyManager;

    @Autowired
    private ResponsePool responsePool;


    public void run() {
        RequestManager.startRequestManager(responsePool);
        System.out.println("RequestManager.startRequestManager");
        proxyManager.initServiceProxyInstance();
        System.out.println("proxyManager.initServiceProxyInstance");

    }

}
