package com.mychat.mybatisTest;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
/**
 * <b>function:</b> 消息接收者； 依赖hawtbuf-1.9.jar
 * @author hoojo
 * @createDate 2013-6-19 下午01:34:27
 * @file MessageReceiver.java
 * @package com.hoo.mq.queue
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class QueueReceiver {
 
    // tcp 地址
    public static final String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static final String TARGET = "hoo.mq.queue";
    
    
    public static void run() throws Exception {
        
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            // 创建链接工厂
            QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
            // 通过工厂创建一个连接
            connection = factory.createQueueConnection();
            // 启动连接
            connection.start();
            
            
            while (true){
            	// 创建一个session会话
                session = connection.createQueueSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
                // 创建一个消息队列
                Queue queue = session.createQueue(TARGET);
                // 创建消息制作者
                javax.jms.QueueReceiver receiver = session.createReceiver(queue);
            	 MapMessage map =(MapMessage) receiver.receive(1000*3600);
            	 System.out.println(map);
                 System.out.println(map.getLong("time") + "接收#" + map.getString("text")+" ["+map.getString("name")+"]");
                 // 提交会话
                 session.commit();
            }
            
            
            
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
        QueueReceiver.run();
    }
}

