package com.dinghai.server.utils;

import com.dinghai.common.config.ZookeeperConfig;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperUtils {

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Autowired
    private ZkClient zkClient;

    public void createRoot() {
        String rootName = zookeeperConfig.getRoot();
        boolean exists = zkClient.exists(rootName);
        if (!exists) {
            zkClient.createPersistent(rootName);
            System.out.println("rpc-server:createRootNode " + rootName);
        }
    }

    public void createEphemeralNode(String nodeName) {
        boolean exists = zkClient.exists(nodeName);
        if (!exists) {
            zkClient.createEphemeral(nodeName);
            System.out.println("rpc-server:createEphemeralNode " + nodeName);
        }
    }

    public String createPersistentNode(String nodeName) {
        String pathName = zookeeperConfig.getRoot() + "/" + nodeName;
        boolean exists = zkClient.exists(pathName);
        if (!exists) {
            zkClient.createPersistent(pathName);
            System.out.println("rpc-server:createPersistentNode " + pathName);
        }
        return pathName;
    }


}
