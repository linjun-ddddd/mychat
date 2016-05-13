package com.mychat.common.mq.imp.string;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicSubscriber;

import com.mychat.common.mq.base.AbstractTopicResponse;
import com.mychat.common.mq.config.ResponseConfig;

public class MultiStringTopicResponse extends AbstractTopicResponse<String> {


	public MultiStringTopicResponse(ResponseConfig responseConfig) {
		// TODO Auto-generated constructor stub
		this.responseConfig=responseConfig;
	}

	@Override
	protected String receive(TopicSubscriber subscriber) throws JMSException {
		// TODO Auto-generated method stub
		TextMessage text = (TextMessage) subscriber.receive(responseConfig.getTimeOut());
		if (text!=null){
			return text.getText();
		}
		return null;
	}
}
