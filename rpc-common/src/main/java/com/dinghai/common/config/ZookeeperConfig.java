package com.dinghai.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperConfig {

    @Value("${dhrpc.cluster.zk.root}")
    private String root;

    @Value("${dhrpc.cluster.zk.port}")
    private int port;

    @Value("${dhrpc.cluster.zk.host}")
    private String host;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
