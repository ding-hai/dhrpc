package com.dinghai.client.pool;

import com.dinghai.client.net.SocketConnector;
import com.dinghai.common.bean.RpcRequest;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

import java.util.concurrent.*;

public class RequestManager {
    private static final ExecutorService executor = new ThreadPoolExecutor(
            30,
            100,
            0,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(30));
    private static ConcurrentHashMap<String, Channel> channelPool = new ConcurrentHashMap<>();

    private static ResponsePool responsePool;

    public static void startRequestManager(ResponsePool pool) {
        responsePool = pool;
    }

    public static void sendRequest(RpcRequest request) {
        System.out.println("rpc-client:sendRequest");
        CountDownLatch latch = new CountDownLatch(1);
        executor.execute(new SocketConnector(request.getRequestId(), latch));
        try {
            latch.await();
            System.out.println("rpc-client:latch.await");
            Channel channel = channelPool.get(request.getRequestId());
            responsePool.registPromise(request.getRequestId(), channel.eventLoop());
            channel.writeAndFlush(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void registerChannel(String requestId, Channel channel) {
        channelPool.put(requestId, channel);
    }

    public static void destroyChannel(String requestId) {
        Channel channel = channelPool.remove(requestId);
        EventLoopGroup eventLoop = channel.eventLoop();
        channel.closeFuture();
        eventLoop.shutdownGracefully();
        System.out.println("rpc-client:destroyChannel");
    }
}
