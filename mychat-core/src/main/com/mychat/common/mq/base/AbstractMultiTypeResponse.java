package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TopicConnection;

public abstract class AbstractMultiTypeResponse<T> implements MultiTypeResponse<T> {

	protected ResponseConfig responseConfig;
	protected final static MessageQueue messageQueue = MessageQueue.getInstance();
	protected T data;

	public ResponseConfig getResponseConfig() {
		return responseConfig;
	}

	public void setResponseConfig(ResponseConfig responseConfig) {
		this.responseConfig = responseConfig;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	protected void closeSession(Session session) {
		// TODO Auto-generated method stub
		if (session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void closeConsumer(MessageConsumer consumer) {
		// TODO Auto-generated method stub
		if (consumer != null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
