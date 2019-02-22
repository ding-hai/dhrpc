package com.dinghai.client.net;

import com.dinghai.client.pool.ResponsePool;
import com.dinghai.common.bean.RpcResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@ChannelHandler.Sharable
public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Autowired
    private ResponsePool responsePool;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        //通知调用者读取响应信息
        responsePool.notifyResponse(response);
        System.out.println("rpc-client:收到rpc响应 "+response.getResult());
    }
}
