package com.dinghai.client.pool;

import com.dinghai.common.bean.RpcResponse;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class ResponsePool {
    private final ConcurrentHashMap<String, Promise<RpcResponse>> responsePool = new ConcurrentHashMap<>();

    public void registPromise(String requestId, EventExecutor eventExecutor) {
        responsePool.put(requestId, new DefaultPromise<>(eventExecutor));
    }

    public RpcResponse fetchResponse(String requestId, int seconds) throws Exception {
        Promise<RpcResponse> promise = responsePool.get(requestId);
        RpcResponse response = null;
        response = promise.get(seconds, TimeUnit.SECONDS);
        System.out.println("rpc-client:in fetchResponse " + response);

        responsePool.remove(requestId);
        RequestManager.destroyChannel(requestId);


        return response;
    }

    public void notifyResponse(RpcResponse response) {
        System.out.println("rpc-client:responsePool=" + responsePool);
        Promise<RpcResponse> promise = responsePool.get(response.getResponseId());
        System.out.println("rpc-client:promise=" + promise);
        if (null != promise) {
            promise.setSuccess(response);
        }
    }
}
