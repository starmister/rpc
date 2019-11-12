package com.swy.protocol.dubbo;


import java.lang.reflect.Method;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import com.swy.framework.RpcRequest;
import com.swy.framework.RpcResponse;


public class InvokeTask implements Runnable{
	
	private RpcRequest invocation;
	private ChannelHandlerContext ctx;

	public InvokeTask(RpcRequest invocation,ChannelHandlerContext ctx) {
		super();
		this.invocation = invocation;
		this.ctx = ctx;
	}


	@Override
	public void run() {
		
        // 从注册中心根据接口，找接口的实现类
		Class impClass = null;
		try {
			impClass = Class.forName(invocation.getImpl());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method method;
        Object result = null;
		try {
			method = impClass.getMethod(invocation.getMethodName(),invocation.getParamTypes());
			//这块考虑实现类，是不是应该在 spring 里面拿
	        result = method.invoke(impClass.newInstance(),invocation.getParams());
		} catch (Exception e) {
			e.printStackTrace();
		}
		RpcResponse rpcResponse = new RpcResponse();
		rpcResponse.setResponseId(invocation.getRequestId());
		rpcResponse.setData(result);
        ctx.writeAndFlush(rpcResponse).addListener(new ChannelFutureListener() {
        	@Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("RPC Server Send message-id respone:" + invocation.getRequestId());
            }
        });

	}

}
