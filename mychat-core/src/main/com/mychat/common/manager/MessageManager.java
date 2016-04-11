package com.mychat.common.manager;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mychat.bean.MessageBean;
import com.mychat.common.mapper.MessageMapper;
import com.mychat.common.mapper.UserMapper;
import com.mychat.common.util.AppContextHelper;
import com.mychat.common.util.StringHelper;

@Scope("singleton")
@Component
public class MessageManager {

	MessageMapper messageMapper=(MessageMapper) AppContextHelper.getInstance().getBean("messageMapper");
	
	public void saveMessage(MessageBean message) {
		// TODO Auto-generated method stub
		if (StringHelper.isEmpty(message.getSendDate())){
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
	public List<MessageBean> getAddFriendMsgByUserId(String toUserId, String page) {
		// TODO Auto-generated method stub
		int pageSize = 5;
		int beginIndex = pageSize*Integer.valueOf(page)-pageSize;
		return messageMapper.getAddFriendMsgByUserId(toUserId,beginIndex,pageSize);
	}

	public int getMaxPageById(String toUserId) {
		// TODO Auto-generated method stub
		int pageSize = 5;
		double maxPage =messageMapper.getAddFriendMsgMaxPageById(toUserId,pageSize);
		return (int)  Math.ceil( maxPage);
	}

}
