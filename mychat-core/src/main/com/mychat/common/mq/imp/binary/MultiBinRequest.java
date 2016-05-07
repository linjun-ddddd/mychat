package com.mychat.common.mq.imp.binary;

import java.io.File;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractNormalRequest;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.config.RequestConfig;

public class MultiBinRequest extends AbstractNormalRequest<byte[]> {

	public MultiBinRequest(String channel, byte[] data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiBinRequest(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	protected void send(Session session, MessageProducer producer, byte[] data) throws JMSException {
		// TODO Auto-generated method stub
		BytesMessage message = session.createBytesMessage();
		message.writeBytes(data);
		producer.send(message);
	}
}
