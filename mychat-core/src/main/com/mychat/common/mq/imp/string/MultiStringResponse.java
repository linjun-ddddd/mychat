package com.mychat.common.mq.imp.string;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import com.mychat.common.mq.base.AbstractNormalResponse;
import com.mychat.common.mq.config.ResponseConfig;

public class MultiStringResponse extends AbstractNormalResponse<String> {

	public MultiStringResponse(ResponseConfig responseConfig) {
		// TODO Auto-generated constructor stub
		this.responseConfig=responseConfig;
	}
	@Override
	protected String receive(MessageConsumer consumer) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage text = (TextMessage) consumer.receive(responseConfig.getTimeOut());
		if (text!=null){
			return text.getText();
		}
		return null;
	}
	
}
