package com.swy.service;


import com.swy.framework.Configuration;
import org.springframework.context.ApplicationContext;

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
		System.out.println("-----------------------------");
	}


}
