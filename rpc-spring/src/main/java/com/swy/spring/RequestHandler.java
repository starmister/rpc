package com.swy.spring;

import com.swy.framework.Configuration;
import com.swy.framework.RpcRequest;
import com.swy.framework.ServiceProvider;

import com.swy.framework.URL;
import com.swy.loadbalance.LoadBalanceEngine;
import com.swy.loadbalance.LoadStrategy;
import com.swy.protocol.Procotol;
import com.swy.protocol.dubbo.DubboProcotol;
import com.swy.register.RegisterCenter4Consumer;
import com.swy.register.ZookeeperRegisterCenter;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;


public class RequestHandler<T> implements InvocationHandler{

    private Class<T> interfaceClass;

    public RequestHandler(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Configuration configuration = Configuration.getInstance();

        Procotol procotol = null;

        if("Dubbo".equalsIgnoreCase(configuration.getProcotol())){
            procotol = new DubboProcotol();
        }else if("Http".equalsIgnoreCase(configuration.getProcotol())){
            //procotol = new HttpProcotol();
        }else if("Socket".equalsIgnoreCase(configuration.getProcotol())){
            //procotol = new SocketProcotol();
        }else{
            procotol = new DubboProcotol();
        }

        //服务接口名称
        String serviceKey = interfaceClass.getName();
        //获取某个接口的服务提供者列表
        RegisterCenter4Consumer registerCenter4Consumer = ZookeeperRegisterCenter.getInstance();
        ServiceProvider providerService = registerCenter4Consumer.getServiceMetaDataMap4Consumer().get(serviceKey);

        URL url = new URL(providerService.getIp(),providerService.getPort());
        String impl = providerService.getServiceObject().toString();
        int timeout = 20000;
        RpcRequest invocation = new RpcRequest(UUID.randomUUID().toString(),interfaceClass.getName(),method.getName(),args, method.getParameterTypes(),impl,timeout);
        Object res = procotol.send(url, invocation);
        return res;
    }


}
