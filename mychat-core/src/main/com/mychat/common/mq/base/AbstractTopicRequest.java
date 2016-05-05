package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

public abstract class AbstractTopicRequest<T> extends AbstractMultiTypeRequest<T> {
	@Override
	public void send() {
		// TODO Auto-generated method stub
		TopicConnection topicConnection=messageQueue.getTopicConnection();
		TopicSession session = null;
		TopicPublisher publisher = null;
		try {
			session = topicConnection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Topic topic = session.createTopic(requestConfig.getTopic());
			// 创建消息制作者
			publisher = session.createPublisher(topic);
			// 设置持久化模式
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			send(session, publisher,this.data);
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 事务处理
		finally {
			closeSender(publisher);
			closeSession(session);
		}

	}

	public abstract void send(Session session, TopicPublisher publisher, T data) throws JMSException;
}
