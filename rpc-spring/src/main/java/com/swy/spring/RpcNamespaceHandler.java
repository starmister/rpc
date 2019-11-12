package com.swy.spring;

import com.swy.framework.Configuration;
import com.swy.spring.parser.ApplicationBeanDefinitionParser;
import com.swy.spring.parser.ProtocolBeanDefinitionParser;
import com.swy.spring.parser.ProviderBeanDefinitionParser;
import com.swy.spring.parser.ConsumerBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author WeiYi
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("procotol", new ProtocolBeanDefinitionParser(Configuration.class));
        registerBeanDefinitionParser("application", new ApplicationBeanDefinitionParser(Configuration.class));
        registerBeanDefinitionParser("provider", new ProviderBeanDefinitionParser(Configuration.class));
        registerBeanDefinitionParser("consumer", new ConsumerBeanDefinitionParser(Configuration.class));
    }
}
