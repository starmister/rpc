package com.swy.spring.parser;


import com.swy.framework.Configuration;
import com.swy.framework.ServiceProvider;
import com.swy.register.RegisterCenter4Provider;
import com.swy.register.ZookeeperRegisterCenter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;

import org.w3c.dom.Element;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 当xml文件中<rpc:provider interf="com.swy.service.HelloService" impl="com.swy.service.HelloServiceImpl" />
 * 启动该组件
 * 主要任务就是把服务注册到zookeeper
 * @author WeiYi
 *
 */
public class ProviderBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public ProviderBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }




    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String interfaces = element.getAttribute("interf");
        String impl = element.getAttribute("impl");

        int port = Configuration.getInstance().getPort();
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            if(port == 0) {
                port = 32115;
            }

            ServiceProvider providerService = new ServiceProvider();
            providerService.setProvider(Class.forName(interfaces));
            providerService.setServiceObject(impl);
            providerService.setIp(ip);
            providerService.setPort(port);
            providerService.setTimeout(5000);
            providerService.setServiceMethod(null);
            providerService.setApplicationName("");
            providerService.setGroupName("nettyrpc");


            //注册到zk,元数据注册中心
            RegisterCenter4Provider registerCenter4Provider = ZookeeperRegisterCenter.getInstance();
            registerCenter4Provider.registerProvider(providerService);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
