package com.mychat.common.mq.base;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;

import com.alibaba.fastjson.JSONObject;

public class MutilTypeMQService {

	private static MessageQueue messageQueue = MessageQueue.getInstance();
	private static ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);
	private static boolean isStart = true;
	
	
	public static void send(final MultiTypeRequest mutilTypeRequest){
		// TODO Auto-generated method stub
		EXECUTOR.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					mutilTypeRequest.send();
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static MultiTypeResponse receive(ResponseConfig responseConfig) {
		// TODO Auto-generated method stub
		Class respClazz=responseConfig.getResponseClazz();
		MultiTypeResponse mResponse = null;
		try {
			mResponse=(MultiTypeResponse) Class.forName(respClazz.getName()).newInstance();
			((AbstractMultiTypeResponse)mResponse).setResponseConfig(responseConfig);
			mResponse.receive();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mResponse;
	}

	public static void start() {
		// TODO Auto-generated method stub
		if (isStart) return;
		messageQueue.start();
		EXECUTOR = Executors.newFixedThreadPool(5);
		isStart=true;
	}
	public static void stop() {
		// TODO Auto-generated method stub
		if (!isStart) return ;
		EXECUTOR.shutdown();
		messageQueue.stop();
		isStart=false;
	}
}
