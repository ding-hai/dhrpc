package com.dinghai.client.proxy;

import com.dinghai.client.bean.RpcServiceProvider;
import com.dinghai.client.cache.ServiceProviderCache;
import com.dinghai.client.config.RpcClientConfig;
import com.dinghai.client.util.ProxyUtil;
import com.dinghai.client.util.SpringBeanFactory;
import com.dinghai.client.util.ZookeeperUtils;
import com.dinghai.common.annotation.RpcClient;
import com.dinghai.common.config.ZookeeperConfig;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
public class ProxyManager {

    @Autowired
    private RpcClientConfig rpcClientConfig;

    @Autowired
    private ProxyUtil proxyUtil;

    @Autowired
    private ServiceProviderCache serviceProviderCache;

    @Autowired
    private ZookeeperUtils zookeeperUtils;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    public void initServiceProxyInstance() {
        Reflections reflections = new Reflections(rpcClientConfig.getServicePackage());
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(RpcClient.class);
        System.out.println("initServiceProxyInstance");
        if (null == classSet || classSet.size() <= 0) {
            return;
        }

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) SpringBeanFactory.context().getAutowireCapableBeanFactory();
        for (Class<?> cls : classSet) {
            String serviceName = cls.getName();
            beanFactory.registerSingleton(serviceName, proxyUtil.newInstance(cls));
            List<RpcServiceProvider> rpcServiceProviders = zookeeperUtils.getServiceProviders(serviceName);
            serviceProviderCache.addCache(zookeeperConfig.getRoot()+"/"+serviceName, rpcServiceProviders);
            zookeeperUtils.subscribe(serviceName);
            System.out.println("beanFactory.registerSingleton");
        }

    }
}
