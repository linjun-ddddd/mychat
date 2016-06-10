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
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.imp.json.MultiJsonQueueRequest;
import com.mychat.common.mq.imp.json.MultiJsonQueueResponse;
import com.mychat.common.mq.imp.json.MultiJsonRequest;
import com.mychat.common.mq.imp.json.MultiJsonResponse;
import com.mychat.common.mq.imp.json.MultiJsonTopicRequest;
import com.mychat.common.mq.imp.json.MultiJsonTopicResponse;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import static org.junit.Assert.assertEquals;

public class JsonMqDemo extends BaseMqDemo {

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
		json.put("flag", "normal");
		MultiJsonRequest request = new MultiJsonRequest(requestConfig);
		request.setData(json);
		request.send();
	}

	@Override
	protected void testQueueRequest() throws JMSException {
		json.put("flag", "queue");
		MultiJsonQueueRequest request = new MultiJsonQueueRequest(requestConfig);
		request.setData(json);
		request.send();
	}

	@Override
	protected void testTopicRequest() throws JMSException {
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2222);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				json.put("flag", "topic");
				MultiJsonTopicRequest request = new MultiJsonTopicRequest(requestConfig);
				request.setData(json);
				request.send();
			}
		}).start();
	}

	@Override
	protected void testNormalResponse() throws JMSException {
		// TODO Auto-generated method stub
		MultiJsonResponse response = new MultiJsonResponse(responseConfig);
		JSONObject data = response.receive().getData();
		System.out.println(data);
		assertEquals("{\"key2\":\"key321\",\"key1\":\"key123\",\"flag\":\"normal\"}", data.toString());
	}

	@Override
	protected void testQueueResponse() throws JMSException {

		MultiJsonQueueResponse response = new MultiJsonQueueResponse(responseConfig);
		JSONObject data = response.receive().getData();
		System.out.println(data);
		assertEquals("{\"key2\":\"key321\",\"key1\":\"key123\",\"flag\":\"queue\"}", data.toString());
	}

	@Override
	protected void testTopicResponse() throws JMSException {
		MultiJsonTopicResponse response = new MultiJsonTopicResponse(responseConfig);
		responseConfig.setTimeOut(1000 * 10);
		JSONObject data = response.receive().getData();
		System.out.println(data);
		assertEquals("{\"key2\":\"key321\",\"key1\":\"key123\",\"flag\":\"topic\"}", data.toString());
	}

}
