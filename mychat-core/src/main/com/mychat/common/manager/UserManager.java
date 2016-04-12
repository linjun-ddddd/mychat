package com.mychat.common.manager;

import java.sql.Timestamp;
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
		UserBean user=userMapper.getUserByNameAndPass(username,md5Password);
		return user;
	}

	public UserBean getUserById(String userid) {
		// TODO Auto-generated method stub
		UserBean user=userMapper.getUserById(userid);
		return user;
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

	public int getMaxPageByName(String _username) {
		// TODO Auto-generated method stub
		int pageSize=6;
		int maxPage = (int) Math.ceil( userMapper.getMaxPageByName("%"+_username+"%",pageSize));
		return maxPage;
	}

	public void saveUser(UserBean user) {
		// TODO Auto-generated method stub
		user.setRegisterTime(new Timestamp(System.currentTimeMillis()).toString());
		userMapper.save(user);
	}

	public void updateUserLastLogin(String id) {
		// TODO Auto-generated method stub
		userMapper.updateUserLastLogin(id,new Timestamp(System.currentTimeMillis()).toString());
	}

	public List<UserBean> getFriendList(String userid) {
		// TODO Auto-generated method stub
		return userMapper.getFriendList(userid);
	}
}
