package com.dinghai.server.register;

import com.dinghai.common.annotation.RpcServer;
import com.dinghai.server.config.ServiceConfig;
import com.dinghai.server.utils.SpringBeanFactory;
import com.dinghai.server.utils.ZookeeperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ServiceRegisterManager {

    @Autowired
    private ZookeeperUtils zookeeperUtils;

    @Autowired
    private ServiceConfig serviceConfig;

    public void registerService() {
        System.out.println("rpc-server:registerService");

        //获取所有的@RpcServer类
        Map<String, Object> beans = SpringBeanFactory.getBeanListWithAnnotation(RpcServer.class);
        if (beans.size() <= 0) {
            return;
        }
        zookeeperUtils.createRoot();
        for (Object bean : beans.values()) {
            //获取Bean的接口

            RpcServer rpcServer = bean.getClass().getAnnotation(RpcServer.class);
            String interfaceName = rpcServer.cls().getName();
            pushToZookeeper(interfaceName);
        }

    }

    public void pushToZookeeper(String interfaceName) {
        String basePath = zookeeperUtils.createPersistentNode(interfaceName);
        String serviceAddress = basePath+"/"+serviceConfig.getHost() + ":" + serviceConfig.getPort();
        zookeeperUtils.createEphemeralNode(serviceAddress);
    }
}
