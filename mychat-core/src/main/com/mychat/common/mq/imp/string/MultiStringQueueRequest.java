package com.mychat.common.mq.imp.string;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractNormalRequest;
import com.mychat.common.mq.base.AbstractQueueRequest;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.RequestConfig;

public class MultiStringQueueRequest extends AbstractQueueRequest<String> {

	public MultiStringQueueRequest(String channel, String data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiStringQueueRequest(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	public void send(QueueSession session, QueueSender sender, String data) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage text = session.createTextMessage(data);
		sender.send(text);
	}

}
