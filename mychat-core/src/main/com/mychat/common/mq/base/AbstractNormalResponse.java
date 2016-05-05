package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TopicConnection;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractNormalResponse<T> extends AbstractMultiTypeResponse<T> {

	public MultiTypeResponse<T> receive() {
		// TODO Auto-generated method stub
		Connection connection = messageQueue.getConnection();
		Session session = null;
		MessageConsumer consumer = null;
		try {
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Destination dest = session.createQueue(responseConfig.getChannel());
			// 创建消息接收者
			consumer = session.createConsumer(dest);
			this.data=receive(consumer);
			// 提交会话
			session.commit();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 事务处理
		finally {
			closeConsumer(consumer);
			closeSession(session);
		}
		return this;
	}

	public abstract T receive(MessageConsumer consumer) throws JMSException;
}
