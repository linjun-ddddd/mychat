package com.mychat.mybatisTest;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.mychat.bean.UserBean;

public interface UserMapper {
	@Select("SELECT * FROM t1_user WHERE id = #{userId}") 
    UserBean getUserById(@Param("userId") String id);

	@Insert("insert into t1_user(name, password, lastLogin, city, age, sex, icon, registerTime, nickname, mysign) values( "
			+ "#{name}, #{password}, #{lastLogin}, #{city}, #{age}, #{sex}, #{icon}, #{registerTime}, #{nickname}, #{mysign})")
	void save(UserBean user); 
}
