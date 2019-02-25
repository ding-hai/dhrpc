package com.dinghai.client.cluster;

import com.dinghai.client.bean.RpcServiceProvider;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements LoadBalanceStrategy {

    @Override
    public RpcServiceProvider select(List<RpcServiceProvider> rpcServiceProviderList) {
        if (null == rpcServiceProviderList || rpcServiceProviderList.size() == 0) {
            return null;
        }
        int size = rpcServiceProviderList.size();
        Random random = new Random();
        int i = random.nextInt(size);
        return rpcServiceProviderList.get(i);
    }
}
