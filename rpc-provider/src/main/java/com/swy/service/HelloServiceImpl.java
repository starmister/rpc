package com.swy.service;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }


    @Override
    public int cal(int a, int b) {
        return a + b;
    }
}
