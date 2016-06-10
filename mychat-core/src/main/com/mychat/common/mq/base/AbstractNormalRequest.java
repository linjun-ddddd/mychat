package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public abstract class AbstractNormalRequest<T> extends AbstractMultiTypeRequest<T> {

	@Override
	public void send() {
		// TODO Auto-generated method stub
		Connection connection = messageQueue.getConnection();
		Session session = null;
		MessageProducer sender = null;
		try {
			// 创建一个会话
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Destination destination = session.createQueue(requestConfig.getChannel());
			// 创建消息制作者
			sender = session.createProducer(destination);
			// 设置持久化模式
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			// 子类的发送方法
			send(session, sender,this.data);
			// 提交会话
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

	protected abstract void send(Session session, MessageProducer producer, T data) throws JMSException;

}
