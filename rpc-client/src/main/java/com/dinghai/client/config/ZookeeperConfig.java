package com.dinghai.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class ZookeeperConfig {

    @Value("${dhrpc.client.zk.root}")
    private String root;

    @Value("${dhrpc.client.zk.port}")
    private int port;

    @Value("${dhrpc.client.zk.host}")
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
