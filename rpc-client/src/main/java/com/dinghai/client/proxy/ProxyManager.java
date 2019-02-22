package com.dinghai.client.proxy;

import com.dinghai.client.config.RpcClientConfig;
import com.dinghai.client.util.ProxyUtil;
import com.dinghai.client.util.SpringBeanFactory;
import com.dinghai.common.annotation.RpcClient;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class ProxyManager {

    @Autowired
    private RpcClientConfig rpcClientConfig;

    @Autowired
    private ProxyUtil proxyUtil;

    public void initServiceProxyInstance() {
        Reflections reflections = new Reflections(rpcClientConfig.getServicePackage());
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(RpcClient.class);
        System.out.println("initServiceProxyInstance");
        if (null == classSet || classSet.size() <= 0) {
            return;
        }

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) SpringBeanFactory.context().getAutowireCapableBeanFactory();
        for (Class<?> cls : classSet) {
            beanFactory.registerSingleton(cls.getName(), proxyUtil.newInstance(cls));
            System.out.println("beanFactory.registerSingleton");
        }

    }
}
