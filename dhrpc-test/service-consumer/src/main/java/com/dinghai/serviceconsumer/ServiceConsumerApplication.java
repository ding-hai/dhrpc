package com.dinghai.serviceconsumer;

import com.dinghai.client.DHRpcClient;
import com.dinghai.client.runner.RpcClientRunner;
import com.dinghai.common.config.ZookeeperConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
		basePackages = "com.dinghai.serviceconsumer",
		basePackageClasses = DHRpcClient.class
)@ComponentScan(
		basePackageClasses = ZookeeperConfig.class
)
@SpringBootApplication
public class ServiceConsumerApplication implements ApplicationRunner{

	@Autowired
	private RpcClientRunner rpcClientRunner;

	public static void main(String[] args) {
		SpringApplication.run(ServiceConsumerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		rpcClientRunner.run();
	}
}
