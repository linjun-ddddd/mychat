package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;

public abstract class AbstractQueueRequest<T> extends AbstractMultiTypeRequest<T> {
	@Override
	public void send() {
		// TODO Auto-generated method stub
		QueueConnection queueConnection=messageQueue.getQueueConnection();
		QueueSession session = null;
		QueueSender sender = null;
		try {
			session = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Queue queue = session.createQueue(requestConfig.getChannel());
			// 创建消息制作者
			sender = session.createSender(queue);
			// 设置持久化模式
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			send(session, sender,this.data);
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 事务处理
		finally {
			closeSender(sender);
			closeSession(session);

		}

	}

	public abstract void send(QueueSession session, QueueSender sender, T data) throws JMSException;
}
