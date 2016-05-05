package com.mychat.common.mq.imp.string;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractNormalRequest;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.RequestConfig;

public class MultiStringRequest extends AbstractNormalRequest<String>{
	
	public MultiStringRequest(String channel,String data) {
		requestConfig=new RequestConfig();
		requestConfig.setChannel(channel);
		this.data=data;
	}
	public MultiStringRequest(RequestConfig requestConfig) {
		this.requestConfig=requestConfig;
	}

	@Override
	public void send(Session session, MessageProducer producer, String data) throws JMSException {
		// TODO Auto-generated method stub
        TextMessage text = session.createTextMessage(data);
        producer.send(text);
	}
}
