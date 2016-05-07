package com.mychat.common.mq.imp.xml;

import javax.jms.BytesMessage;
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
import javax.xml.transform.TransformerException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Document;

import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractQueueRequest;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.config.RequestConfig;

public class MultiXmlQueueRequest extends AbstractQueueRequest<Document> {

	public MultiXmlQueueRequest(String channel, Document data) {
		requestConfig = new RequestConfig();
		requestConfig.setChannel(channel);
		this.data = data;
	}

	public MultiXmlQueueRequest(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	@Override
	protected void send(QueueSession session, QueueSender sender, Document data) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage message = session.createTextMessage(XmlHelper.xml2String(data));
		sender.send(message);
	}
}
