package com.dinghai.server.net;

import com.dinghai.common.bean.RpcRequest;
import com.dinghai.common.bean.RpcResponse;
import com.dinghai.server.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

public class RequestHandler extends SimpleChannelInboundHandler<RpcRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setResponseId(request.getRequestId());
        String className = request.getClassName();
        String methodName =  request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        try {
            Object targetObject = SpringBeanFactory.getBean(Class.forName(className));
            Method targetMethod = targetObject.getClass().getMethod(methodName, parameterTypes);
            Object result = targetMethod.invoke(parameters);
            response.setResult(result);
        } catch (Throwable cause) {
            response.setCause(cause);

        }

        channelHandlerContext.writeAndFlush(response);





    }
}
