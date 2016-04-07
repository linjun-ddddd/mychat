package com.mychat.common.manager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mychat.bean.UserBean;
import com.mychat.common.mapper.UserMapper;
import com.mychat.common.util.SpringHelper;
import com.mychat.common.util.Utils;

@Scope("singleton")
@Component
public class UserManager {

	UserMapper userMapper=(UserMapper) SpringHelper.getInstance().getBean("userMapper");
	
	public UserBean getUser(String username, String password) {
		// TODO Auto-generated method stub
		String md5Password = Utils.md5(password);
		UserBean user=userMapper.getUserById(username,md5Password);
		return user;
	}
}
