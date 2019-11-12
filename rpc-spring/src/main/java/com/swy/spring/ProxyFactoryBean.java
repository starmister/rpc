package com.swy.spring;


import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class ProxyFactoryBean<T> implements FactoryBean<T> {


    private Class<T> interfaceClass;




    public ProxyFactoryBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }



    @Override
    public T getObject() throws Exception {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new RequestHandler(interfaceClass));
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }


}
