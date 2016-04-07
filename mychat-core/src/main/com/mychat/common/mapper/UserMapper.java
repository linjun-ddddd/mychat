package com.mychat.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.mychat.bean.UserBean;

@Component("userMapper")
public interface UserMapper {
	@Select("SELECT * FROM user WHERE name = #{username} and password = #{password}") 
    UserBean getUserById(@Param("username") String username,@Param("password") String password); 
}
