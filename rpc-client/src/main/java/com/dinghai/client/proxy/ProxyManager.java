package com.dinghai.client.proxy;

import com.dinghai.client.config.RpcClientConfig;
import com.dinghai.client.util.SpringBeanFactory;
import com.dinghai.common.annotation.RpcClient;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.util.Set;


@Component
public class ProxyManager {

    @Autowired
    private RpcClientConfig rpcClientConfig;

    public void initServiceProxyInstace() {
        Reflections reflections = new Reflections(rpcClientConfig.getServicePackage());
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(RpcClient.class);
        if (null == classSet || classSet.size() <= 0) {
            return;
        }


        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) SpringBeanFactory.context().getAutowireCapableBeanFactory();
        for (Class<?> cls : classSet) {

        }

    }
}
