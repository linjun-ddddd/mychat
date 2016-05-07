package com.mychat.common.mq.config;

public class RequestConfig {
	
	String channel = "";
	String topic = "";
	
	
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
	
}
