package com.liqiang.nettyTest2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.StringValueExp;
import javax.swing.text.StringContent;

import com.liqiang.SimpeEcode.Message;
import com.liqiang.SimpeEcode.MessageHead;

public class nettyMain {
	public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Server server = new Server(8081);
				server.startServer();

			}
		}).start();
	
	}

}
