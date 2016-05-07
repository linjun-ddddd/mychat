package com.mychat.mqtest;

import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSONObject;
 
 
/**
 * <b>function:</b> Queue 方式消息发送者
 * @author hoojo
 * @createDate 2013-6-19 下午04:34:36
 * @file QueueSender.java
 * @package com.hoo.mq.topic
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class TopicSender {
    
    // 发送次数
    public static final int SEND_NUM = 1;
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String DESTINATION = "hoo.mq.topic";
    
    /**
     * <b>function:</b> 发送消息
     * @author hoojo
     * @createDate 2013-6-19 下午12:05:42
     * @param session 会话
     * @param publisher 发布者
     * @throws Exception
     */    
    public static void sendMessage(TopicSession session, TopicPublisher publisher) throws Exception {
    	JSONObject json = new JSONObject();
    	json.put("key1", "key123");
		json.put("key2", "key321");
		ObjectMessage message = session.createObjectMessage(json);
		publisher.publish(message);
    }
    
    public static void run() throws Exception {
        
        TopicConnection connection = null;
        TopicSession session = null;
        try {
            // 创建链接工厂
            TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createTopicConnection();
            // 创建一个session会话
            session = connection.createTopicSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Topic topic = session.createTopic(TestConstant.TOPIC);
            // 创建消息发送者
            TopicPublisher publisher = session.createPublisher(topic);
            // 设置持久化模式
            publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
            // 启动连接
            connection.start();
            sendMessage(session, publisher);
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
    
    public static void main(String[] args) throws Exception {
        TopicSender.run();
    }
}
