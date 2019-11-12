package com.swy.spring.parser;


import com.swy.framework.Configuration;
import com.swy.framework.ServiceProvider;
import com.swy.framework.URL;
import com.swy.protocol.Procotol;
import com.swy.protocol.dubbo.DubboProcotol;
import com.swy.protocol.dubbo.channelpool.NettyChannelPoolFactory;
import com.swy.register.ZookeeperRegisterCenter;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * 当xml文件中<rpc:procotol procotol="Dubbo" port="3230" serialize="ProtoStuff" role="provider" address="118.190.36.109:2181"/>
 * 启动该组件
 * 这里使用dubbo 所以我们要启动netty
 * @author WeiYi
 *
 */
public class ProtocolBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public ProtocolBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }




    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String pro = element.getAttribute("procotol");
        int port = Integer.parseInt(element.getAttribute("port"));
        Configuration.getInstance().setProcotol(pro);
        Configuration.getInstance().setPort(port);
        Configuration.getInstance().setSerialize(element.getAttribute("serialize"));
        Configuration.getInstance().setStragety(element.getAttribute("stragety"));
        Configuration.getInstance().setRole(element.getAttribute("role"));
        Configuration.getInstance().setAddress(element.getAttribute("address"));
        if("provider".equals(element.getAttribute("role"))){
            Procotol procotol = null;
                if("Dubbo".equalsIgnoreCase(pro)){
                    procotol = new DubboProcotol();
                }else if("Http".equalsIgnoreCase(pro)){
                    //procotol = new HttpProcotol();
                }else if("Socket".equalsIgnoreCase(pro)){
                    //procotol = new SocketProcotol();
                }else{
                    procotol = new DubboProcotol();
                }

                try {
                    InetAddress addr = InetAddress.getLocalHost();
                    String ip = addr.getHostAddress();
                    if(port == 0){
                        port = 32115;
                    }
                    URL url = new URL(ip,port);
                    procotol.start(url);

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }else{
            //获取服务注册中心
            ZookeeperRegisterCenter registerCenter4Consumer = ZookeeperRegisterCenter.getInstance();
            //初始化服务提供者列表到本地缓存
            registerCenter4Consumer.initProviderMap();
            //初始化Netty Channel
            Map<String, ServiceProvider> providerMap = registerCenter4Consumer.getServiceMetaDataMap4Consumer();
            if (MapUtils.isEmpty(providerMap)) {
                throw new RuntimeException("service provider list is empty.");
            }
            NettyChannelPoolFactory.getInstance().initNettyChannelPoolFactory(providerMap);
        }
        return null;
    }
}
