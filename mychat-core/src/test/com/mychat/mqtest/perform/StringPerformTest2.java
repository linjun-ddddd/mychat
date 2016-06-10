package com.mychat.mqtest.perform;

import javax.jms.JMSException;

import org.junit.Test;

import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.config.ResponseConfig;
import com.mychat.common.mq.imp.string.MultiStringQueueRequest;
import com.mychat.common.mq.imp.string.MultiStringQueueResponse;
import com.mychat.common.mq.imp.string.MultiStringRequest;
import com.mychat.common.mq.imp.string.MultiStringResponse;
import com.mychat.common.mq.imp.string.MultiStringTopicRequest;
import com.mychat.common.mq.imp.string.MultiStringTopicResponse;
import com.mychat.mqtest.TestConstant;
import com.mychat.mqtest.example.MessageReceiver;
import com.mychat.mqtest.example.QueueReceiver;
import com.mychat.mqtest.example.sender.StringMessageSender;
import com.mychat.mqtest.example.sender.StringQueueSender;
import com.mychat.mqtest.example.sender.StringTopicSender;

public class StringPerformTest2 {

	private static String data = "Hello World";
	private static String bigdata = "Hello World~!.123";
	private static int size = 1000;

	public static void main(String[] args) {
		MessageQueue.getInstance();
		for (int i=0;i<12;i++) bigdata+=bigdata;
		System.out.println(bigdata.getBytes().length);
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
		StringMessageSender.SEND_NUM=size;
		StringQueueSender.SEND_NUM=size;
		StringTopicSender.SEND_NUM=size;
		StringMessageSender.data=data;
		StringQueueSender.data=data;
		StringTopicSender.data=data;
		MessageReceiver.SEND_NUM=size;
		QueueReceiver.SEND_NUM=size;
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
	}

	private static void testTopicPerformRequest() {
		// TODO Auto-generated method stub
		try {
			StringTopicSender.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testQueuePerformRespone() {
		// TODO Auto-generated method stub
		try {
			QueueReceiver.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testQueuePerformRequest() {
		// TODO Auto-generated method stub
		try {
			StringQueueSender.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testNormalPerformResponse() {
		// TODO Auto-generated method stub
		try {
			MessageReceiver.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testNormalPerformRequest() {
		try {
			StringMessageSender.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
