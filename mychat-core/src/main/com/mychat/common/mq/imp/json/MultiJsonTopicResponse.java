package com.mychat.common.mq.imp.json;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractMultiTypeResponse;
import com.mychat.common.mq.base.AbstractTopicResponse;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.MultiTypeResponse;
import com.mychat.common.mq.base.RequestConfig;
import com.mychat.common.mq.base.ResponseConfig;

public class MultiJsonTopicResponse extends AbstractTopicResponse<JSONObject> {

	public MultiJsonTopicResponse() {
		// TODO Auto-generated constructor stub
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "com.alibaba.fastjson,java.util");
	}

	@Override
	public JSONObject receive(TopicSubscriber subscriber) throws JMSException {
		// TODO Auto-generated method stub
		ObjectMessage message = (ObjectMessage) subscriber.receive(responseConfig.getTimeOut());
		if (message!=null){
			return (JSONObject) message.getObject();
		}
		return null;
	}
}
