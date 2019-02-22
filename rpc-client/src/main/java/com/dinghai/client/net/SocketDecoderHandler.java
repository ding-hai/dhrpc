package com.dinghai.client.net;

import com.dinghai.common.bean.RpcRequest;
import com.dinghai.common.bean.RpcResponse;
import com.dinghai.common.serialize.SerializeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class SocketDecoderHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();
        System.out.println("rpc-client:message len should be " + len);

        if (byteBuf.readableBytes() < len) {
            byteBuf.resetReaderIndex();
        } else {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println("rpc-client:read byte length="+bytes.length);
            RpcResponse response = SerializeUtil.deserialize(bytes, RpcResponse.class);
            System.out.println("rpc-client:response="+response);
            list.add(response);
        }
    }
}
