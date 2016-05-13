package com.mychat.mqtest.example;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.imp.binary.BinaryHelper;
import com.mychat.mqtest.TestConstant;

/**
 * <b>function:</b> 消息接收者
 * 
 * @author hoojo
 * @createDate 2013-6-19 下午01:34:27
 * @file MessageReceiver.java
 * @package com.hoo.mq.jms
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class MessageReceiver {

	// tcp 地址
	public static final String BROKER_URL = "tcp://localhost:61616";
	// 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	public static String DESTINATION = TestConstant.CHANNEL;
	public static int SEND_NUM = 5;

	public static void run() throws Exception {
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "com.alibaba.fastjson,java.util");
		Connection connection = null;
		Session session = null;
		// 创建链接工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
		for (int i = 0; i < SEND_NUM; i++) {
			try {
				// 通过工厂创建一个连接
				connection = factory.createConnection();
				// 启动连接
				connection.start();
				// 创建一个session会话
				session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				// 创建一个消息队列
				Destination destination = session.createQueue(DESTINATION);
				// 创建消息制作者
				MessageConsumer consumer = session.createConsumer(destination);

				// 接收数据的时间（等待） 100 ms
				Message message = consumer.receive(1000 * 100);
				processMessage(message);
				// 提交会话
				session.commit();

			} catch (Exception e) {
				throw e;
			} finally {
				// 关闭释放资源
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	private static void processMessage(Message message) throws JMSException {
		// TODO Auto-generated method stub
		if (message==null) return;
		if (message instanceof TextMessage){
			TextMessage msg = (TextMessage) message;
			msg.getText();
		}else if (message instanceof ObjectMessage){
			ObjectMessage msg = (ObjectMessage) message;
			JSONObject obj=(JSONObject)msg.getObject();
		}else if (message instanceof BytesMessage){
			BytesMessage msg = (BytesMessage) message;
			BinaryHelper.getByteFromMessage(msg);
		}
	}

	public static void main(String[] args) throws Exception {
		
		MessageReceiver.run();
	}
}
