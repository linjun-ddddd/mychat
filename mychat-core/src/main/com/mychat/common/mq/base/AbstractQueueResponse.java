package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnection;

public abstract class AbstractQueueResponse<T> extends AbstractMultiTypeResponse<T>{
	public AbstractMultiTypeResponse<T> receive() {
		// TODO Auto-generated method stub
		QueueConnection queueConnection=messageQueue.getQueueConnection();
		QueueSession session = null;
		QueueReceiver receiver = null;
		try {
			session = queueConnection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Queue queue = session.createQueue(responseConfig.getChannel());
			// 创建消息接收者
			receiver = session.createReceiver(queue);
			this.data=receive(receiver);
			// 提交会话
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 事务处理
		finally {
			closeConsumer(receiver);
			closeSession(session);
		}
		return this;
	}

	protected abstract T receive(QueueReceiver receiver) throws JMSException;	
}
