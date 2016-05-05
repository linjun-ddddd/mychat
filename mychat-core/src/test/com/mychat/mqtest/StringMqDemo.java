package com.mychat.mqtest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQStreamMessage;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.MutilTypeMQService;
import com.mychat.common.mq.base.RequestConfig;
import com.mychat.common.mq.imp.json.MultiJsonQueueRequest;
import com.mychat.common.mq.imp.json.MultiJsonQueueResponse;
import com.mychat.common.mq.imp.json.MultiJsonRequest;
import com.mychat.common.mq.imp.json.MultiJsonResponse;
import com.mychat.common.mq.imp.json.MultiJsonTopicRequest;
import com.mychat.common.mq.imp.json.MultiJsonTopicResponse;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringQueueResponse;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import com.mychat.common.mq.imp.string.MultiStringResponse;
import com.mychat.common.mq.imp.string.MultiStringTopicRequest;
import com.mychat.common.mq.imp.string.MultiStringTopicResponse;

import static org.junit.Assert.assertEquals;

public class StringMqDemo extends BaseMqDemo {

	@Test
	public void testStart() throws JMSException {
		// normal
		testNormalRequest();
		testNormalResponse();

		// queue
		testQueueRequest();
		testQueueResponse();

		// topic
		testTopicRequest();
		testTopicResponse();
	}
	

	@Override
	protected void testNormalRequest() throws JMSException {
		// TODO Auto-generated method stub
		MultiStringRequest request = new MultiStringRequest(requestConfig);
		request.setData("[normal] "+str);
		MutilTypeMQService.send(request);
	}

	@Override
	protected void testQueueRequest() throws JMSException {
		MultiStringQueueRequest request = new MultiStringQueueRequest(requestConfig);
		request.setData("[queue] "+str);
		MutilTypeMQService.send(request);
	}

	@Override
	protected void testTopicRequest() throws JMSException {
		MultiStringTopicRequest request = new MultiStringTopicRequest(requestConfig);
		request.setData("[topic] "+str);
		MutilTypeMQService.send(request);
	}

	@Override
	protected void testNormalResponse() throws JMSException {
		// TODO Auto-generated method stub
		responseConfig.setResponseClazz(MultiStringResponse.class);
		MultiStringResponse response = (MultiStringResponse) MutilTypeMQService.receive(responseConfig);
		String data = response.getData();
		System.out.println(data);
		assertEquals("[normal] Hello World!!~", data.toString());
	}

	@Override
	protected void testQueueResponse() throws JMSException {
		responseConfig.setResponseClazz(MultiStringQueueResponse.class);
		MultiStringQueueResponse response = (MultiStringQueueResponse) MutilTypeMQService.receive(responseConfig);
		String data = response.getData();
		System.out.println(data);
		assertEquals("[queue] Hello World!!~", data.toString());
	}

	@Override
	protected void testTopicResponse() throws JMSException {
		responseConfig.setResponseClazz(MultiStringTopicResponse.class);
		MultiStringTopicResponse response = (MultiStringTopicResponse) MutilTypeMQService.receive(responseConfig);
		String data = response.getData();
		System.out.println(data);
		assertEquals("[topic] Hello World!!~", data.toString());
	}

}
