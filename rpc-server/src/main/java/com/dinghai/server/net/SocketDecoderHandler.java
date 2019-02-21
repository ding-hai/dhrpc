package com.dinghai.server.net;

import com.dinghai.common.bean.RpcRequest;
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
        int len = byteBuf.readInt();
        byteBuf.markReaderIndex();
        if (byteBuf.readableBytes() < len) {
            byteBuf.resetReaderIndex();
        } else {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            RpcRequest request = SerializeUtil.deserialize(bytes, RpcRequest.class);
            list.add(request);
        }
    }
}
