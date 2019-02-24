package com.dinghai.server.net;

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
    private RequestHandler requestHandler;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new SocketDecoderHandler());
        pipeline.addLast(new SocketEncoderHandler());
        pipeline.addLast(requestHandler);

    }
}
