package com.dinghai.serviceinterface;

import com.dinghai.common.annotation.RpcClient;

@RpcClient
public interface ServiceInterface {
    String compute(int start, int end);
}
