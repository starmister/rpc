package com.swy.protocol.dubbo;

import com.swy.framework.RpcRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
		System.out.println("server channelRead...");
		System.out.println(ctx.channel().remoteAddress() + "->server:" + rpcRequest.toString());
		InvokeTask it = new InvokeTask(rpcRequest,ctx);
		NettyServer.submit(it);
	}

	
}
