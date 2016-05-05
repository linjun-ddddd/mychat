package com.mychat.common.mq.base;

public class ResponseConfig {
	
	private String channel = "";
	private String topic = "";
	private Class responseClazz;

	private long timeOut = 1000L;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public Class getResponseClazz() {
		return responseClazz;
	}
	public void setResponseClazz(Class responseClazz) {
		this.responseClazz = responseClazz;
	}
	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
	

}
