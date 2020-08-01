package com.pt.ptchat.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpointConfig;

// 自定义配置类
@Component
public class SpringContextHelper extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    //  通过手动注入applicationContext上下文获取Bean
    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringContextHelper.context = applicationContext;
    }

}
