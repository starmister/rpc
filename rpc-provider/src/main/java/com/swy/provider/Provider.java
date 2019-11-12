package com.swy.provider;


import com.swy.framework.Configuration;
import com.swy.protocol.Procotol;
import com.swy.protocol.dubbo.DubboProcotol;
import com.swy.register.Register;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.swy.framework.URL;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.UnknownHostException;

/**
 *
 * 启动服务端，注册服务
 *
 */
public class Provider{

	public static void main(String[] args) throws UnknownHostException {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rpc.xml");
		System.out.println(Configuration.getInstance().getAddress());
	}


}
