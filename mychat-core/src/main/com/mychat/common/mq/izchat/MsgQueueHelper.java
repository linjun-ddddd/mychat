package com.mychat.common.mq.izchat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;

import org.apache.activemq.ActiveMQMessageConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.MessageBean;
import com.mychat.common.manager.MessageManager;
import com.mychat.common.manager.UserManager;
import com.mychat.common.mq.base.MessageQueue;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.config.ResponseConfig;
import com.mychat.common.mq.imp.json.MultiJsonQueueRequest;
import com.mychat.common.mq.imp.json.MultiJsonQueueResponse;
import com.mychat.common.util.Constants;

public class MsgQueueHelper {

	private final static MessageQueue messageQueue = MessageQueue.getInstance();
	private final static Map<String,String> sessionMap = new HashMap<>();//保存当前用户的session
	
	
	public static void sendMessage(MessageBean message) throws JMSException {
		// TODO Auto-generated method stub
		RequestConfig requestConfig = new RequestConfig();
		JSONObject data = JSONObject.parseObject(JSON.toJSONString(message));
		String touserid=message.getToUserId();
		requestConfig.setChannel(touserid);
		MultiJsonQueueRequest request = new MultiJsonQueueRequest(requestConfig);
		request.setData(data);
		request.send();
	}
	public static JSONObject receiveMsg(String userid) {
		// TODO Auto-generated method stub
		ResponseConfig responseConfig = new ResponseConfig();
		responseConfig.setTimeOut(10 * 3600 * 1000L);
		responseConfig.setChannel(userid);
		MultiJsonQueueResponse response = new MultiJsonQueueResponse(responseConfig);
		JSONObject data = response.receive().getData();
		sessionMap.put(userid, "flag");
		return data;
	}
	/*
	 public static void sendMessage(MessageBean message) throws JMSException {
		// TODO Auto-generated method stub
		String touserid=message.getToUserId();
	 	QueueSession session = null;
	 	javax.jms.QueueSender sender =null;
        try {
           
            // 鍒涘缓涓�涓猻ession浼氳瘽
        	session = messageQueue.getQueueConnection().createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
        	
            // 鍒涘缓涓�涓秷鎭槦鍒�
            Queue queue =session.createQueue(touserid);
            // 鍒涘缓娑堟伅鍙戦�佽��
            sender = session.createSender(queue);
            // 璁剧疆鎸佷箙鍖栨ā寮�
            sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // 鍙戦�佹秷鎭�
            MapMessage map = session.createMapMessage();
            
            map.setString("id", message.getId());
            map.setString("fromuserid", message.getFromUserId());
            map.setString("toUserId", message.getToUserId());
            map.setString("type", message.getType());
            map.setString("title", message.getTitle());
            map.setString("data", message.getData());
            map.setString("status", message.getStatus());
            map.setString("sendDate", message.getSendDate());
            //map.setString("fromusername", messageBean.getFromUserName());
            //map.setString("tousername", messageBean.getToUserName());
            System.out.println("send message:"+map);
            sender.send(map);
            // 鎻愪氦浼氳瘽
            session.commit();
        } finally {
        	if (sender!=null){
        		sender.close();
        	}
			if (session!=null){
				session.close();
			}
		}
	}
	 * 
	 * */
	/*
	public static MapMessage receiveMsg(String userid) throws JMSException {
		// TODO Auto-generated method stub
		MapMessage message = null;
		long receiveTimeOut = 10 * 3600 * 1000L;
		QueueSession session =null;
		ActiveMQMessageConsumer receiver =null;
		//保证每个用户每次有且仅有一个receiver
		try {
			// 创建一个session会话
			session = messageQueue.getQueueConnection().createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建一个消息队列
			Queue queue = session.createQueue(userid);
			// 创建消息接收者
			receiver = (ActiveMQMessageConsumer) session.createReceiver(queue);
			
			sessionMap.put(userid, "flag");
			message = (MapMessage) receiver.receive(receiveTimeOut);
			System.out.println("receive message:"+message);
			// 提交会话
			session.commit();
			
		}catch (javax.jms.IllegalStateException ex){
			//session可以重复close，不可以重复commit
			//session已经被commit的异常，不必处理
		}finally {
			if (receiver!=null){
				receiver.close();
			}
			if (session!=null){
				session.close();
			}
			sessionMap.remove(userid);
		}
        return message;
	}*/

	public static void destory(String userid) throws JMSException {
		// TODO Auto-generated method stub
		if (sessionMap.get(userid)!=null){
			//发送结束消息,以让用户receiver结束
			MessageBean messageBean = new MessageBean();
			messageBean.setToUserId(userid);
			messageBean.setType(Constants.END_MESSAGE_TYPE);
			sendMessage(messageBean);
			sessionMap.remove(userid);
		}
	}

	
	
	//http://www.cnblogs.com/hoojo/p/active_mq_jms_apache_activeMQ.html
}
