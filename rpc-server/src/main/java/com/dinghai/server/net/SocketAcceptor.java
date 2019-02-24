package com.dinghai.server.net;

import com.dinghai.common.config.ZookeeperConfig;
import com.dinghai.server.config.ServiceConfig;
import com.dinghai.server.utils.SpringBeanFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class SocketAcceptor implements Runnable {

    private ServiceConfig serviceConfig;
    private SocketInitializer socketInitializer;

    public SocketAcceptor() {
        this.serviceConfig = SpringBeanFactory.getBean(ServiceConfig.class);
        this.socketInitializer = SpringBeanFactory.getBean(SocketInitializer.class);
    }

    @Override
    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(socketInitializer);
        try {
            System.out.println("rpc-server:绑定在端口"+serviceConfig.getPort());
            ChannelFuture future = serverBootstrap.bind(serviceConfig.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();

        }

    }
}
