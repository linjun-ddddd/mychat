package com.mychat.common.mq.imp.binary;

import java.io.File;
import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import com.alibaba.fastjson.JSONObject;
import com.mychat.common.mq.base.AbstractMultiTypeRequest;
import com.mychat.common.mq.base.AbstractMultiTypeResponse;
import com.mychat.common.mq.base.AbstractNormalResponse;
import com.mychat.common.mq.base.MultiTypeRequest;
import com.mychat.common.mq.base.MultiTypeResponse;
import com.mychat.common.mq.config.RequestConfig;
import com.mychat.common.mq.config.ResponseConfig;

public class MultiBinResponse extends AbstractNormalResponse<byte[]> {

	public MultiBinResponse() {
		// TODO Auto-generated constructor stub
		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "com.alibaba.fastjson,java.util,java.io");
	}

	public MultiBinResponse(ResponseConfig responseConfig) {
		// TODO Auto-generated constructor stub
		this.responseConfig=responseConfig;
	}

	@Override
	protected byte[] receive(MessageConsumer consumer) throws JMSException {
		// TODO Auto-generated method stub
		BytesMessage message = (BytesMessage) consumer.receive(responseConfig.getTimeOut());
		if (message != null) {
			byte[] byteData= BinaryHelper.getByteFromMessage(message);
			return byteData;
		}
		return null;
	}

}
