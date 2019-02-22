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
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();

        if (byteBuf.readableBytes() < len) {
            byteBuf.resetReaderIndex();
        } else {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println("rpc-server:read byte length="+bytes.length);
            RpcRequest request = SerializeUtil.deserialize(bytes, RpcRequest.class);
            System.out.println("rpc-server:receive from client "+request.getRequestId());
            list.add(request);
        }
    }
}
