package com.liqiang.nettyTest2;

import java.util.Date;

import com.liqiang.SimpeEcode.Message;
import com.liqiang.SimpeEcode.MessageHead;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHandle extends ChannelInboundHandlerAdapter {
	
	Client client;
    public  ClientHandle(Client client) {
		// TODO Auto-generated constructor stub
       this.client=client;
    }
    /**
	 * 读写超时事事件
     * @throws Exception 
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent=((IdleStateEvent) evt);
			/**
			 * 如果没有收到服务端的写 则表示服务器超时 判断是否断开连接
			 */
	        if(idleStateEvent.state()==IdleState.READER_IDLE) {
	        	System.out.println("服务器无响应");
	        	if(!ctx.channel().isOpen()) {
	        		System.out.println("正在重连");
	        		client.connection();
	        		System.out.println("重连成功");
	        	}
	        }else if(idleStateEvent.state()==IdleState.WRITER_IDLE) {
	        	//如果没有触发写事件则向服务器发送一次心跳包
	        	System.out.println("正在向服务端发送心跳包");
	        	MessageHead head=new MessageHead();
	        	byte[]content="".getBytes();
				head.setCreateDate(new Date());
				head.setType("ping");
				head.setLength(content.length);
				Message pingMessage=new Message(head,content);
				head.setToken(pingMessage.buidToken());
	 			ctx.writeAndFlush(pingMessage);
	        }
		}else {
			super.userEventTriggered(ctx, evt);
		}
	}
	//建立连接时回调
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("与服务器建立连接成功");
		client.setServerChannel(ctx);
		client.setConnection(true);
		//ctx.fireChannelActive();//如果注册多个handle 下一个handel的事件需要触发需要调用这个方法
		
	}
	//读取服务器发送信息时回调
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message=(Message) msg;
		if(message.getHead().getType().equals("ping")) {
			//表示是心跳包 不做任何业务处理
		}else {
			// TODO Auto-generated method stub
			System.out.println(msg.toString());
		}
	
	}

	//发生异常时回调
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
	    System.out.println("发生异常 与服务器断开连接");
		ctx.close();//关闭连接
	}
}
