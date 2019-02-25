package com.dinghai.client.util;

import com.dinghai.client.bean.RpcServiceProvider;
import com.dinghai.client.cache.ServiceProviderCache;
import com.dinghai.common.config.ZookeeperConfig;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZookeeperUtils {
    @Autowired
    private ZkClient zkClient;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Autowired
    private ServiceProviderCache serviceProviderCache;

    public List<RpcServiceProvider> getServiceProviders(String serviceName) {
        String path = zookeeperConfig.getRoot() + "/" + serviceName;
        List<String> serviceProviders = zkClient.getChildren(path);
        return parse(serviceProviders);
    }

    public void subscribe(String serviceName) {
        String path = zookeeperConfig.getRoot() + "/" + serviceName;
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                serviceProviderCache.updateCache(s, parse(list));
                System.out.println("rpc-client:serviceProviderCache hashcode"+serviceProviderCache.hashCode());
            }
        });
    }

    private static List<RpcServiceProvider> parse(List<String> strList) {
        List<RpcServiceProvider> rpcServiceProviderList = new ArrayList<>();
        for (String str : strList) {
            String[] items = str.split(":");
            String host = items[0];
            int port = Integer.parseInt(items[1]);
            rpcServiceProviderList.add(new RpcServiceProvider(host, port));
        }
        return rpcServiceProviderList;
    }
}
