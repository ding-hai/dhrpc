package com.dinghai.serviceconsumer.controller;

import com.dinghai.client.util.SpringBeanFactory;
import com.dinghai.serviceinterface.ServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test")
    public String test() {
        ServiceInterface serviceInterface = SpringBeanFactory.getBean(ServiceInterface.class);
        return serviceInterface.compute(0, 100);
    }
}
