package com.dinghai.server.config;

import com.dinghai.common.config.ZookeeperConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Bean
    public ZkClient zkClient() {
        String address = zookeeperConfig.getHost() + ":" + zookeeperConfig.getPort();
        System.out.println("rpc-server:zk " + address);
        return new ZkClient(address, 5000);
    }

}
