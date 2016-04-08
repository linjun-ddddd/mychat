package com.mychat.common.manager;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mychat.bean.MessageBean;
import com.mychat.common.mapper.MessageMapper;
import com.mychat.common.mapper.UserMapper;
import com.mychat.common.util.AppContextHelper;

@Scope("singleton")
@Component
public class MessageManager {

	MessageMapper messageMapper=(MessageMapper) AppContextHelper.getInstance().getBean("messageMapper");
	
	public void saveMessage(MessageBean message) {
		// TODO Auto-generated method stub
		if (message.getSendDate()==null){
			message.setSendDate(new Timestamp(System.currentTimeMillis()).toString());
		}
		messageMapper.insertMessage(message);
	}

	public void updateMessageStatus(String messageId, String status) {
		// TODO Auto-generated method stub
		messageMapper.updateMessageStatus(messageId,status);
	}

	/*
	 * page从1开始
	 * */
	public List<MessageBean> getMsgByUserId(String toUserId, String page) {
		// TODO Auto-generated method stub
		int pageSize = 5;
		int beginInde = pageSize*Integer.valueOf(page)-pageSize;
		return messageMapper.getMsgByUserId(toUserId,beginInde,pageSize);
	}

}
