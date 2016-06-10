package com.mychat.common.mq.base;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TopicConnection;

import com.mychat.common.mq.config.RequestConfig;

public abstract class AbstractMultiTypeRequest<T> implements MultiTypeRequest<T> {

	protected RequestConfig requestConfig;
	protected final static MessageQueue messageQueue = MessageQueue.getInstance();
	protected T data;

	public RequestConfig getRequestConfig() {
		return requestConfig;
	}

	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	protected void closeSender(MessageProducer sender) {
		// TODO Auto-generated method stub
		if (sender != null) {
			try {
				sender.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

}
