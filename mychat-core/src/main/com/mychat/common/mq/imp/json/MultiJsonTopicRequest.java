package com.mychat.common.mq.imp.json;

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
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractTopicRequest;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.RequestConfig;

public class MultiJsonTopicRequest extends AbstractTopicRequest<JSONObject> {

	public MultiJsonTopicRequest(String channel, JSONObject data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiJsonTopicRequest(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	public void send(Session session, TopicPublisher publisher, JSONObject data) throws JMSException {
		// TODO Auto-generated method stub
		ObjectMessage message = session.createObjectMessage(data);
		publisher.publish(message);
	}

}
