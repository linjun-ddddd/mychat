package com.mychat.mybatisTest;

import com.mychat.bean.UserBean;

public class UserManager {
	UserMapper userMapper;
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper=userMapper;
	}
	
	  public UserBean getUserById(String userid) {
          return userMapper.getUserById(userid); 
       }
}
