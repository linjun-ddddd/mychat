package com.mychat.common.mq.base;

import javax.jms.JMSException;

public interface MultiTypeResponse<T> {
	public MultiTypeResponse<T> receive() throws JMSException ;
}
