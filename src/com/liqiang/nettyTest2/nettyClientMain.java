package com.liqiang.nettyTest2;

import java.util.Date;

import com.liqiang.SimpeEcode.Message;
import com.liqiang.SimpeEcode.MessageHead;

public class nettyClientMain {
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Client client1 = new Client("127.0.0.1", 8081);
				client1.connection();
				String content = "哈哈哈哈！";
				byte[] bts = content.getBytes();
				MessageHead head = new MessageHead();
				// 令牌生成时间
				head.setCreateDate(new Date());
                head.setType("message");
				head.setLength(bts.length);
				Message message = new Message(head, bts);	
				message.getHead().setToken(message.buidToken());
				client1.sendMsg(message);
				

			}
		}).start();
		
	}
}
