package com.dinghai.server.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class SocketInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new SocketDecoderHandler());
        pipeline.addLast(new SocketEncoderHandler());
        pipeline.addLast(new RequestHandler());

    }
}
