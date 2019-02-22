package com.dinghai.client.util;

import com.dinghai.client.pool.RequestManager;
import com.dinghai.client.pool.ResponsePool;
import com.dinghai.common.bean.RpcRequest;
import com.dinghai.common.bean.RpcResponse;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Component
public class ProxyUtil {

    @Autowired
    private ResponsePool responsePool;

    public  <T> T newInstance(Class<T> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(new ProxyCallBackHandler());
        return (T) enhancer.create();
    }

    class ProxyCallBackHandler implements MethodInterceptor {


        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            String requestId = UUID.randomUUID().toString();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();
            RpcRequest request = new RpcRequest(requestId, className, methodName, parameterTypes, objects);

            RequestManager.sendRequest(request);
            System.out.println("rpc-client:before fetchResponse");
            RpcResponse response = responsePool.fetchResponse(requestId,10);
            System.out.println("rpc-client:before fetchResponse");
            System.out.println("rpc-client:"+response);

            if (null == response) {
                return null;
            }
            if (response.isError()) {
                throw response.getCause();
            }

            return response.getResult();
        }
    }

}


