package com.mychat.common.mq.config;

import org.apache.activemq.ActiveMQConnection;

public class MessageQueueConfig {
	private String brokerUrl = "";
	private String mqUserName = "";
	private String mqPassword = "";
	public String getBrokerUrl() {
		return brokerUrl;
	}
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}
	public String getMqUserName() {
		return mqUserName;
	}
	public void setMqUserName(String mqUserName) {
		this.mqUserName = mqUserName;
	}
	public String getMqPassword() {
		return mqPassword;
	}
	public void setMqPassword(String mqPassword) {
		this.mqPassword = mqPassword;
	}

}
