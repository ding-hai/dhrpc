package com.dinghai.serviceprovider;

import com.dinghai.common.config.ZookeeperConfig;
import com.dinghai.server.DHRpcServer;
import com.dinghai.server.runner.RpcServerRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
        basePackageClasses = DHRpcServer.class,
        basePackages = "com.dinghai.serviceprovider"
)
@ComponentScan(
        basePackageClasses = ZookeeperConfig.class
)
@SpringBootApplication
public class ServiceProviderApplication implements ApplicationRunner {
    @Autowired
    private RpcServerRunner rpcServerRunner;

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        rpcServerRunner.run();

    }
}
