package com.mychat.mqtest;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.config.ResponseConfig;
import com.mychat.common.mq.imp.json.MultiJsonQueueRequest;
import com.mychat.common.mq.imp.json.MultiJsonQueueResponse;
import com.mychat.common.mq.imp.json.MultiJsonRequest;
import com.mychat.common.mq.imp.json.MultiJsonResponse;
import com.mychat.common.mq.imp.json.MultiJsonTopicRequest;
import com.mychat.common.mq.imp.json.MultiJsonTopicResponse;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import com.mychat.common.mq.imp.xml.XmlHelper;

import junit.framework.TestCase;

public abstract class BaseMqDemo {
	
	RequestConfig requestConfig = new RequestConfig();
	ResponseConfig responseConfig = new ResponseConfig();
	JSONObject json = new JSONObject();
	String str = "Hello World!!~";
	File file = new File("pom.xml"); 
	TestConstant testConstant = new TestConstant();
	Document document = XmlHelper.file2Xml(new File("pom.xml"));
	
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		json.put("key1", "key123");
		json.put("key2", "key321");
		requestConfig.setChannel(TestConstant.CHANNEL);
		requestConfig.setTopic(TestConstant.TOPIC);
		responseConfig.setChannel(TestConstant.CHANNEL);
		responseConfig.setTopic(TestConstant.TOPIC);
		responseConfig.setTimeOut(1000*10);
	}
	
	@After
	public void tearDown() throws Exception {
		// 关闭资源
	}
	
	protected abstract void testNormalRequest() throws JMSException ;
	
	protected abstract void testQueueRequest() throws JMSException ;
	
	protected abstract  void testTopicRequest() throws JMSException ;
	
	protected abstract  void testNormalResponse() throws JMSException ;
	
	protected abstract  void testQueueResponse() throws JMSException ;
	
	protected abstract  void testTopicResponse() throws JMSException ;
}
