package com.liqiang.nettyTest2;

import java.util.concurrent.TimeUnit;

import com.liqiang.SimpeEcode.MessageDecode;
import com.liqiang.SimpeEcode.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private Client client;
	public  ClientChannelInitializer(Client client) {
		// TODO Auto-generated constructor stub
		this.client=client;
	}
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		// TODO Auto-generated method stub
		socketChannel.pipeline()
		//表示5秒向服务器发送一次心跳包   10秒没接收到服务器端信息表示服务器端挂了
		 .addLast("ping",new IdleStateHandler(10, 5, 0, TimeUnit.SECONDS))
		.addLast("decoder",new MessageEncoder())
		.addLast("encoder",new MessageDecode())
		.addLast(new ClientHandle(client));//注册处理器
		
	}
}
