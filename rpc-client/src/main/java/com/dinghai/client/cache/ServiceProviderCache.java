package com.dinghai.client.cache;

import com.dinghai.client.bean.RpcServiceProvider;
import com.dinghai.common.annotation.RpcServer;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ServiceProviderCache {

    private ConcurrentHashMap<String, List<RpcServiceProvider>> map = new ConcurrentHashMap<>();

    public List<RpcServiceProvider> getRpcServiceProviderList(String serviceName) {
        return map.get(serviceName);
    }

    public void addCache(String name, List<RpcServiceProvider> rpcServiceProviders) {
        List<RpcServiceProvider> oldRpcServiceProviderList = map.getOrDefault(name, new ArrayList<>());
        if (oldRpcServiceProviderList.size() == 0) {
            map.put(name, rpcServiceProviders);
        }
        oldRpcServiceProviderList.addAll(rpcServiceProviders);
    }
    public void updateCache(String name, List<RpcServiceProvider> rpcServiceProviders) {
        System.out.println("updateCache："+name+"-"+rpcServiceProviders);
        map.put(name, rpcServiceProviders);
    }

    @Override
    public String toString() {
        return "ServiceProviderCache{" +
                "map=" + map +
                '}';
    }
}
