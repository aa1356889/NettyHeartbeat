package com.liqiang.SimpeEcode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageHead {
	 private int headData=0X76;//协议开始标志
	    private int length;//包的长度
	    private String token;
	    private Date createDate;
	    private String type;//消息类型  ping表示心跳包
		public int getHeadData() {
			return headData;
		}
		public void setHeadData(int headData) {
			this.headData = headData;
		}
		public int getLength() {
			return length;
		}
		public void setLength(int length) {
			this.length = length;
		}
		
		
	    public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		@Override
	    public String toString() {
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	// TODO Auto-generated method stub
	    	return "headData："+headData+",length:"+length+",token:"+token+",createDate："+	simpleDateFormat.format(createDate);
	    }
}
