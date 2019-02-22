package com.dinghai.client.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class SocketInitializer extends ChannelInitializer<Channel> {
    @Autowired
    private ResponseHandler responseHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new SocketDecoderHandler());
        pipeline.addLast(new SocketEncoderHandler());
        System.out.println("responseHandler" + responseHandler);
        pipeline.addLast(responseHandler);

    }
}
