package com.mychat.common.mq.imp.json;

import java.io.File;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
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
import com.mychat.common.mq.base.AbstractQueueRequest;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.config.RequestConfig;

public class MultiJsonQueueRequest extends AbstractQueueRequest<JSONObject>{

	public MultiJsonQueueRequest(String channel,JSONObject data) {
		requestConfig=new RequestConfig();
		requestConfig.setChannel(channel);
		this.data=data;
	}
	public MultiJsonQueueRequest(RequestConfig requestConfig) {
		this.requestConfig=requestConfig;
	}

	@Override
	protected void send(QueueSession session, QueueSender sender, JSONObject data) throws JMSException {
		// TODO Auto-generated method stub
		ObjectMessage message = session.createObjectMessage(data);
        sender.send(message);
	}
}
