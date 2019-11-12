package com.swy.protocol.dubbo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyClientPool {

    public  static Map<String,NettyClientHandler> holder = new ConcurrentHashMap<>();
}
