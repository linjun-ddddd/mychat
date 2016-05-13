package com.mychat.common.mq.base;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mychat.common.mq.config.MessageQueueConfig;

public class MessageQueue {

	// 单例
	private static MessageQueue messageQueue = null;
	// tcp 地址
	private String brokerUrl = "tcp://localhost:61616";
	private String mqUserName = ActiveMQConnection.DEFAULT_USER;
	private String mqPassword = ActiveMQConnection.DEFAULT_PASSWORD;
	// 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	private Connection connection = null;
	private QueueConnection queueConnection = null;
	private TopicConnection topicConnection = null;

	public void start() {
		// 创建链接工厂
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(mqUserName, mqPassword, brokerUrl);
		// 通过工厂创建连接
		try {
			connection = factory.createConnection();
			queueConnection = factory.createQueueConnection();
			topicConnection = factory.createTopicConnection();
			connection.start();
			queueConnection.start();
			topicConnection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stop() {
		// TODO Auto-generated method stub
		try {
			if (connection != null) {
				connection.close();
			}
			if (queueConnection != null) {
				queueConnection.close();
			}
			if (topicConnection != null) {
				topicConnection.close();
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized MessageQueue getInstance() {
		// TODO Auto-generated method stub
		if (messageQueue == null) {
			messageQueue = new MessageQueue();
			messageQueue.start();
		}
		return messageQueue;
	}

	public static synchronized MessageQueue getInstance(MessageQueueConfig config) {
		// TODO Auto-generated method stub
		if (messageQueue == null) {
			messageQueue = new MessageQueue();
			messageQueue.brokerUrl = config.getBrokerUrl();
			messageQueue.mqUserName = config.getMqUserName();
			messageQueue.mqPassword = config.getMqPassword();
			messageQueue.start();
		}
		return messageQueue;
	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return connection;
	}

	public QueueConnection getQueueConnection() {
		// TODO Auto-generated method stub
		return queueConnection;
	}

	public TopicConnection getTopicConnection() {
		// TODO Auto-generated method stub
		return topicConnection;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

}
