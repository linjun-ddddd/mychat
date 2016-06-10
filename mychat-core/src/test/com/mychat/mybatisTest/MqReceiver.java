package com.mychat.mybatisTest;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;

import java.util.Enumeration;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.MessageQueue;

public class MqReceiver {
	private final static MessageQueue messageQueue = MessageQueue.getInstance();
	public static void main(String[] args) throws JMSException {
		MapMessage message = null;
		long receiveTimeOut = 1000*3600*10L;
		QueueSession session =null;
		ActiveMQMessageConsumer receiver =null;
		//保证每个用户每次有且仅有一个receiver
		while (true){
			try {
				// 创建一个session会话
				session = messageQueue.getQueueConnection().createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				// 创建一个消息队列
				Queue queue = session.createQueue("27141");
				// 创建消息接收者
				receiver = (ActiveMQMessageConsumer) session.createReceiver(queue);
				message = (MapMessage) receiver.receive(receiveTimeOut);
				JSONObject data = new JSONObject();
				Enumeration<String> eu=message.getMapNames();
				while (eu.hasMoreElements()) {
					String key=eu.nextElement();
					String value=message.getString(key);
					data.put(key, value);
				}
				System.out.println("receive message:"+data);
				// 提交会话
				session.commit();
				
			}catch (javax.jms.IllegalStateException ex){
				//session可以重复close，不可以重复commit
				//session已经被commit的异常，不必处理
			}finally {
				if (receiver!=null){
					receiver.close();
				}
				if (session!=null){
					session.close();
				}
			}
		}
		
	}
}
