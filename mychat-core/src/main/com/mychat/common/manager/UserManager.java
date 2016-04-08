package com.mychat.common.manager;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mychat.bean.UserBean;
import com.mychat.common.mapper.UserMapper;
import com.mychat.common.util.AppContextHelper;
import com.mychat.common.util.CommonHelper;

@Scope("singleton")
@Component
public class UserManager {

	UserMapper userMapper=(UserMapper) AppContextHelper.getInstance().getBean("userMapper");
	
	public UserBean getUser(String username, String password) {
		// TODO Auto-generated method stub
		String md5Password = CommonHelper.md5(password);
		UserBean user=userMapper.getUserById(username,md5Password);
		return user;
	}

	public UserBean getUserById(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserBean> getLastChatUser(String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * page从1开始
	 * */
	public List<UserBean> searchFriendByName(String _username, String page) {
		// TODO Auto-generated method stub
		final int pageSize = 6;
		final int beginIndex = pageSize*Integer.valueOf(page)-pageSize;
		List<UserBean> userList=userMapper.searchFriendByName("%"+_username+"%",beginIndex,pageSize);
		return userList;
	}
}
