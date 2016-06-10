package com.mychat.common.mq.imp.string;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;

import com.mychat.common.mq.base.AbstractQueueResponse;
import com.mychat.common.mq.config.ResponseConfig;

public class MultiStringQueueResponse extends AbstractQueueResponse<String> {

	public MultiStringQueueResponse(ResponseConfig responseConfig) {
		// TODO Auto-generated constructor stub
		this.responseConfig=responseConfig;
	}

	@Override
	protected String receive(QueueReceiver receiver) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage text = (TextMessage) receiver.receive(responseConfig.getTimeOut());
		if (text!=null){
			return text.getText();
		}
		return null;
	}
}
