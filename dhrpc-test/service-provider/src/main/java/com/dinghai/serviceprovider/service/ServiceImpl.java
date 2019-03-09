package com.dinghai.serviceprovider.service;

import com.dinghai.common.annotation.RpcServer;
import com.dinghai.serviceinterface.ServiceInterface;
import org.springframework.stereotype.Component;

@Component
@RpcServer(cls = ServiceInterface.class)
public class ServiceImpl implements ServiceInterface {
    @Override
    public String compute(int start, int end) {
        System.out.println("service-provider:execute");
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += i;
        }
        return "" + sum;
    }
}
