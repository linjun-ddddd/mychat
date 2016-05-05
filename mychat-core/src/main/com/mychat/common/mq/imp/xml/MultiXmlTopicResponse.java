package com.mychat.common.mq.imp.xml;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractMultiTypeResponse;
import com.mychat.common.mq.base.AbstractNormalResponse;
import com.mychat.common.mq.base.AbstractTopicResponse;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.MultiTypeResponse;
import com.mychat.common.mq.base.RequestConfig;

public class MultiXmlTopicResponse extends AbstractTopicResponse<Document> {

	public MultiXmlTopicResponse() {
	}

	@Override
	public Document receive(TopicSubscriber subscriber) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage message = (TextMessage) subscriber.receive(responseConfig.getTimeOut());
		String xmlStr = message.getText();
		return XmlHelper.string2Xml(xmlStr);
	}
}
