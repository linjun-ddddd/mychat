package com.mychat.common.mq.imp.xml;

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
import javax.xml.transform.TransformerException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Document;

import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractNormalRequest;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.config.RequestConfig;

public class MultiXmlRequest extends AbstractNormalRequest<Document> {

	public MultiXmlRequest(String channel, Document data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiXmlRequest(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	protected void send(Session session, MessageProducer producer, Document data) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage message = session.createTextMessage(XmlHelper.xml2String(data));
		producer.send(message);
	}
}
