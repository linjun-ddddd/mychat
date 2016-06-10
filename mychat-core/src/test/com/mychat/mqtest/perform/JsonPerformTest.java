package com.mychat.mqtest.perform;

import javax.jms.JMSException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.config.ResponseConfig;
import com.mychat.common.mq.imp.json.*;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringQueueResponse;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import com.mychat.common.mq.imp.string.MultiStringResponse;
import com.mychat.common.mq.imp.string.MultiStringTopicRequest;
import com.mychat.common.mq.imp.string.MultiStringTopicResponse;
import com.mychat.mqtest.TestConstant;
import com.mychat.mqtest.example.MessageReceiver;
import com.mychat.mqtest.example.sender.StringMessageSender;

public class JsonPerformTest {

	private static JSONObject data = new JSONObject();
	private static JSONObject bigdata = new JSONObject();
	private static RequestConfig requestConfig = new RequestConfig();
	private static int size = 1000;
	private static ResponseConfig responseConfig = new ResponseConfig();

	public static void main(String[] args) {
		MessageQueue.getInstance();
		data.put("data", "Hello World");
		String _bigdata = "Hello World~!.123";
		for (int i=0;i<12;i++) {
			_bigdata+=bigdata;
		}
		bigdata.put("data", _bigdata);
		while (size>1){
			test();
//			testBig();
			size/=10;
		}
		// 关闭资源
		MessageQueue.getInstance().stop();
	}
	public static void testBig() {
		data=bigdata;
		test();
	}
	public static void test() {
		long begin, end;
		requestConfig.setChannel(TestConstant.CHANNEL);
		requestConfig.setTopic(TestConstant.TOPIC);
		responseConfig.setChannel(TestConstant.CHANNEL);
		responseConfig.setTopic(TestConstant.TOPIC);
		System.out.println("共 " + size + " 条数据");

		begin = System.currentTimeMillis();
		testNormalPerformRequest();
		end = System.currentTimeMillis();
		System.out.println("[normal]发送花费时间：" + (end - begin) + " ms");

		begin = System.currentTimeMillis();
		testNormalPerformResponse();
		end = System.currentTimeMillis();
		System.out.println("[normal]接收花费时间：" + (end - begin) + " ms");

		begin = System.currentTimeMillis();
		testQueuePerformRequest();
		end = System.currentTimeMillis();
		System.out.println("[queue]发送花费时间：" + (end - begin) + " ms");

		begin = System.currentTimeMillis();
		testQueuePerformRespone();
		end = System.currentTimeMillis();
		System.out.println("[queue]接收花费时间：" + (end - begin) + " ms");

		begin = System.currentTimeMillis();
		testTopicPerformRequest();
		end = System.currentTimeMillis();
		System.out.println("[topic]发送花费时间：" + (end - begin) + " ms");

		System.out.println("=============\n");
//		begin = System.currentTimeMillis();
//		testTopicPerformResponse();
//		end = System.currentTimeMillis();
//		System.out.println("[topic]接收花费时间：" + (end - begin) + " ms");
		
	}

	private static void testTopicPerformResponse() {
		// TODO Auto-generated method stub
		MultiJsonTopicResponse response = new MultiJsonTopicResponse(responseConfig);
		for (int i=0;i<size;i++){
			response.receive();
		}
	}

	private static void testTopicPerformRequest() {
		// TODO Auto-generated method stub
		MultiJsonTopicRequest request = new MultiJsonTopicRequest(requestConfig);
		request.setData(data);
		for (int i = 0; i < size; i++) {
			request.send();
		}
	}

	private static void testQueuePerformRespone() {
		// TODO Auto-generated method stub
		MultiJsonQueueResponse response = new MultiJsonQueueResponse(responseConfig);
		for (int i=0;i<size;i++){
			response.receive();
		}
	}

	private static void testQueuePerformRequest() {
		// TODO Auto-generated method stub
		MultiJsonQueueRequest request = new MultiJsonQueueRequest(requestConfig);
		request.setData(data);
		for (int i = 0; i < size; i++) {
			request.send();
		}
	}

	private static void testNormalPerformResponse() {
		// TODO Auto-generated method stub
		MultiJsonResponse response = new MultiJsonResponse(responseConfig);
		for (int i=0;i<size;i++){
			response.receive();
		}
	}

	private static void testNormalPerformRequest() {
		MultiJsonRequest request = new MultiJsonRequest(requestConfig);
		request.setData(data);
		for (int i = 0; i < size; i++) {
			request.send();
		}
	}

}
