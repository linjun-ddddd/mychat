package com.mychat.common.mq.base;

import javax.jms.JMSException;

public interface MultiTypeRequest<T> {
	public void send() throws JMSException ;
	
}
