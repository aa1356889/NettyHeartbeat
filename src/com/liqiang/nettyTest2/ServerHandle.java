package com.liqiang.nettyTest2;

import java.util.Date;

import com.liqiang.SimpeEcode.Message;
import com.liqiang.SimpeEcode.MessageHead;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerHandle extends ChannelInboundHandlerAdapter {

	private Server server;

	public ServerHandle(Server server) {
		// TODO Auto-generated constructor stub
		this.server = server;
	}
	/**
	 * 读写超时事事件
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent) {
			IdleStateEvent event=(IdleStateEvent)evt;
			//如果读超时
			if(event.state()==IdleState.READER_IDLE) {
			        System.out.println("有客户端超时了");
			        ctx.channel().close();//关闭连接
			}
		}else {
			super.userEventTriggered(ctx, evt);
		}
		
	}

	// 建立连接时回调
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("有客户端建立连接了");
		server.addClient(ctx);
		// ctx.fireChannelActive();//pipeline可以注册多个handle 这里可以理解为是否通知下一个Handle继续处理
	}

	// 接收到客户端发送消息时回调
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message=(Message)msg;
		if(message.getHead().getType().equals("ping")) {
			//表示心跳包 服务端响应心跳包  而不做相关业务处理
			MessageHead head=new MessageHead();
			byte[] content="".getBytes();
			head.setCreateDate(new Date());
			head.setType("ping");
			head.setLength(content.length);
			Message pingMessage=new Message(head,content);
			head.setToken(pingMessage.buidToken());
 			ctx.writeAndFlush(pingMessage);
		}else {
			System.out.println("server接收到客户端发送信息:" + msg.toString());
		}
		// TODO Auto-generated method stub
		
		// ctx.fireChannelRead(msg);pipeline可以注册多个handle 这里可以理解为是否通知下一个Handle继续处理
	}

	// 通信过程中发生异常回调
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		// super.exceptionCaught(ctx, cause);
		ctx.close();// 发生异常关闭通信通道
	    System.out.println("发生异常与客户端失去连接");
	   
	    cause.printStackTrace();
		// ctx.fireExceptionCaught(cause);pipeline可以注册多个handle 这里可以理解为是否通知下一个Handle继续处理
	}
}
