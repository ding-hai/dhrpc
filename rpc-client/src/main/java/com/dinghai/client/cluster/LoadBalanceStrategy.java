package com.dinghai.client.cluster;

import com.dinghai.client.bean.RpcServiceProvider;

import java.util.List;

public interface LoadBalanceStrategy {

    RpcServiceProvider select(List<RpcServiceProvider> rpcServiceProviderList);


}
