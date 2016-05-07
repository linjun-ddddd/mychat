package com.mychat.common.mq.imp.binary;

import javax.jms.BytesMessage;
import javax.jms.JMSException;

public class BinaryHelper {

	public static byte[] getByteFromMessage(BytesMessage message) throws JMSException {
		// TODO Auto-generated method stub
		StringBuffer stringBuffer = new StringBuffer();
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = message.readBytes(buf)) != -1) {
			stringBuffer.append(new String(buf,0,len));
		}
		return stringBuffer.toString().getBytes();
	}

}
