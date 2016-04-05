package com.mychat.mybatisTest;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mychat.bean.UserBean;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE id = #{userId}") 
    UserBean getUserById(@Param("userId") String id); 
}
