package com.dinghai.client.util;

import com.dinghai.common.bean.RpcRequest;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

public class ProxyUtil {
    public static <T> T newInstace(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new ProxyCallBackHandler());
    }

    class ProxyCallBackHandler implements MethodInterceptor {


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String requestId = UUID.randomUUID().toString();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            Class<?> parameterTypes = method.getParameterTypes();
            RpcRequest request = new RpcRequest(requestId, className, methodName, parameterTypes, objects);
            // TODO: 19-2-21  发送请求 获取结果
            //发送请求
            //获取结果
            return ;
        }
    }

}


