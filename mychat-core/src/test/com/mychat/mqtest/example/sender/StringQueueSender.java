package com.mychat.mqtest.example.sender;

import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mychat.mqtest.TestConstant;

/**
 * <b>function:</b> Queue 方式消息发送者
 * 
 * @author hoojo
 * @createDate 2013-6-19 下午04:34:36
 * @file QueueSender.java
 * @package com.hoo.mq.queue
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class StringQueueSender {

	// 发送次数
	public static int SEND_NUM = 5;
	// tcp 地址
	public static final String BROKER_URL = "tcp://localhost:61616";
	// 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
	public static final String DESTINATION = TestConstant.CHANNEL;
	public static String data = "";

	/**
	 * <b>function:</b> 发送消息
	 * 
	 * @author hoojo
	 * @createDate 2013-6-19 下午12:05:42
	 * @param session
	 * @param sender
	 * @throws Exception
	 */
	public static void sendMessage(QueueSession session, javax.jms.QueueSender sender) throws Exception {

		TextMessage text = session.createTextMessage(data);
		sender.send(text);

	}

	public static void run() throws Exception {

		QueueConnection connection = null;
		QueueSession session = null;
		// 创建链接工厂
		QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
		for (int i = 0; i < SEND_NUM; i++) {
			try {
				// 通过工厂创建一个连接
				connection = factory.createQueueConnection();
				// 启动连接
				connection.start();
				// 创建一个session会话
				session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				// 创建一个消息队列
				Queue queue = session.createQueue(DESTINATION);
				// 创建消息发送者
				javax.jms.QueueSender sender = session.createSender(queue);
				// 设置持久化模式
				sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				sendMessage(session, sender);
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

	public static void main(String[] args) throws Exception {
		StringQueueSender.run();
	}
}
