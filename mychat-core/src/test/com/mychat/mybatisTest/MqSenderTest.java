package com.mychat.mybatisTest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mychat.common.mq.base.MessageQueue;

public class MqSenderTest {
	private final static MessageQueue messageQueue = MessageQueue.getInstance();
	public static void main(String[] args) throws JMSException {
		String touserid="27141";
	 	QueueSession session = null;
	 	javax.jms.QueueSender sender =null;
	    try {
	    	session = messageQueue.getQueueConnection().createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	       for (int i=0;i<5;i++){
	    	// 创建一个session会话
		    	
		        // 创建一个消息队列
		        Queue queue =session.createQueue(touserid);
		        // 创建消息发送者
		        sender = session.createSender(queue);
		        // 设置持久化模式
		        sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		        
		        // 发送消息
		        MapMessage map = session.createMapMessage();
		        
		        map.setString("id", "123");
		        map.setString("fromuserid", "123");
		        map.setString("toUserId", touserid);
		        map.setString("type", "1");
		        map.setString("data", "消息"+i);
		        //map.setString("fromusername", messageBean.getFromUserName());
		        //map.setString("tousername", messageBean.getToUserName());
		        System.out.println("send message:"+map);
		        sender.send(map);
		        // 提交会话
	       }
	       session.commit();
	        
	    } finally {
	    	if (sender!=null){
	    		sender.close();
	    	}
			if (session!=null){
				session.close();
			}
		}
	}

}
