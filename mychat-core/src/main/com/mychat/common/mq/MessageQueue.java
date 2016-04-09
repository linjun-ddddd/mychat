package com.mychat.common.mq;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageQueue {

	//单例
	private static MessageQueue messageQueue = null;
	// tcp 地址
	private String brokerUrl = "tcp://localhost:61616";
	// 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	private QueueConnection connection = null;
	private Map<String, Queue> queueMap = new HashMap<>();
	private long receiveTimeOut = 1000 * 60 *5 ;
	

	private void start() {
		// 创建链接工厂
		QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, brokerUrl);
		// 通过工厂创建一个连接
		try {
			connection = factory.createQueueConnection();
			connection.start();
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
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static synchronized MessageQueue getInstance() {
		// TODO Auto-generated method stub
		if (messageQueue==null){
			messageQueue = new MessageQueue();
			messageQueue.start();
		}
		return messageQueue;
	}

	public QueueConnection getConnection() {
		// TODO Auto-generated method stub
		return connection;
	}

	public long getTimeOut() {
		// TODO Auto-generated method stub
		return receiveTimeOut;
	}

}
