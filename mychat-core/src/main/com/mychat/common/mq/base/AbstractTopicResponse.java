package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

public abstract class AbstractTopicResponse<T>  extends AbstractMultiTypeResponse<T>{
	
	public MultiTypeResponse<T> receive() {
		// TODO Auto-generated method stub
		TopicConnection topicConnection=messageQueue.getTopicConnection();
		TopicSession session = null;
		TopicSubscriber subscriber = null;
		try {
			session = topicConnection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Topic topic = session.createTopic(responseConfig.getTopic());
			// 创建消息接收者
			subscriber = session.createSubscriber(topic);
			this.data=receive(subscriber);
			// 提交会话
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 事务处理
		finally {
			closeConsumer(subscriber);
			closeSession(session);
		}
		return this;
	}

	public abstract T receive(TopicSubscriber subscriber) throws JMSException;
	
	
}
