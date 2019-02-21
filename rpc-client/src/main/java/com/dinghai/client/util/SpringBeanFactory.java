package com.dinghai.client.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactory  implements ApplicationContextAware{
    private static ApplicationContext context;

    public static   ApplicationContext context() {
        return context;
    }

    public static <T> T getBean(Class<T> cls) {
        return context.getBean(cls);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
