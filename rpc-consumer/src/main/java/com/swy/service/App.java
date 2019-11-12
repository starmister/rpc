package com.swy.service;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rpc.xml");
        HelloService service = (HelloService) applicationContext.getBean(HelloService.class);
        UserService userService = (UserService) applicationContext.getBean(UserService.class);
        int res = service.cal(2,2);
        userService.add("swy");
        System.out.println("HelloService调用结果: " + res);
    }
}
