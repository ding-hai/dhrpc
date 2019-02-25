package com.dinghai.client.pool;

import com.dinghai.client.bean.RpcServiceProvider;
import com.dinghai.client.cache.ServiceProviderCache;
import com.dinghai.client.cluster.LoadBalanceStrategy;
import com.dinghai.client.cluster.RandomStrategy;
import com.dinghai.client.net.SocketConnector;
import com.dinghai.client.util.SpringBeanFactory;
import com.dinghai.common.bean.RpcRequest;
import com.dinghai.common.config.ZookeeperConfig;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

import java.util.List;
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
    private static LoadBalanceStrategy strategy;
    private static ServiceProviderCache cache;
    private static ZookeeperConfig zookeeperConfig;

    public static void startRequestManager(ResponsePool pool) {
        responsePool = pool;
        strategy = new RandomStrategy();
        cache = SpringBeanFactory.getBean(ServiceProviderCache.class);
        zookeeperConfig = SpringBeanFactory.getBean(ZookeeperConfig.class);
    }

    public static void sendRequest(RpcRequest request) {
        System.out.println("rpc-client:sendRequest");
        System.out.println("sendRequest cache " + cache);
        List<RpcServiceProvider> rpcServiceProviders = cache.getRpcServiceProviderList(zookeeperConfig.getRoot()+"/"+request.getClassName());
        System.out.println("rpc-client:rpcServiceProviders="+rpcServiceProviders);
        //选择一个策略
        //从缓存中选择一个host
        RpcServiceProvider rpcServiceProvider = strategy.select(rpcServiceProviders);
        System.out.println("rpc-client:rpcServiceProvider="+rpcServiceProvider);
        //把这个host信息传递给SocketConnector

        CountDownLatch latch = new CountDownLatch(1);
        executor.execute(new SocketConnector(request.getRequestId(), latch,rpcServiceProvider));
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
