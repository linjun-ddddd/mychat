package com.mychat.common.mq.imp.file;

import java.io.File;

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
import com.mychat.common.mq.base.RequestConfig;

public class MultiFileRequest extends AbstractNormalRequest<File> {

	public MultiFileRequest(String channel, File data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiFileRequest(RequestConfig requestConfig) {
		this.requestConfig=requestConfig;
	}

	@Override
	public void send(Session session,MessageProducer producer, File data) throws JMSException {
		// TODO Auto-generated method stub
		ObjectMessage message = session.createObjectMessage(data);
		producer.send(message);
	}
}
