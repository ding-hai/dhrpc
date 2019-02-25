package com.dinghai.client.net;

import com.dinghai.client.bean.RpcServiceProvider;
import com.dinghai.client.pool.RequestManager;
import com.dinghai.client.util.SpringBeanFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;


public class SocketConnector implements Runnable{
    private String requestId;
    private CountDownLatch latch;


    private SocketInitializer socketInitializer;
    private RpcServiceProvider rpcServiceProvider;


    public SocketConnector(String requestId, CountDownLatch latch, RpcServiceProvider rpcServiceProvider) {
        this.rpcServiceProvider = rpcServiceProvider;
        this.requestId = requestId;
        this.latch = latch;
        this.socketInitializer = SpringBeanFactory.getBean(SocketInitializer.class);
    }

    @Override
    public void run() {
        System.out.println("rpc-client:开始连接");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(rpcServiceProvider.getHost(), rpcServiceProvider.getPort())
                .handler(socketInitializer);

        try {
            ChannelFuture future = bootstrap.connect().sync();
            if(future.isSuccess()){
                System.out.println("rpc-client:连接成功");
                RequestManager.registerChannel(requestId,future.channel());

                System.out.println("rpc-client:注册到RequestManager");
                latch.countDown();
                System.out.println("rpc-client:latch.countDown");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
