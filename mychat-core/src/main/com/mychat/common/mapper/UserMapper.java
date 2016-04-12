package com.mychat.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.mychat.bean.UserBean;

@Component("userMapper")
public interface UserMapper {
	final String table = "t1_user";
	
	@Select("SELECT * FROM t1_user WHERE name = #{username} and password = #{password}") 
    UserBean getUserByNameAndPass(@Param("username") String username,@Param("password") String password);

	@Select("SELECT * FROM t1_user WHERE name like #{_username} limit #{beginIndex},#{pageSize}") 
	List<UserBean> searchFriendByName(@Param("_username") String _username, @Param("beginIndex")int beginIndex, @Param("pageSize")int pageSize); 
	
	@Insert("insert into t1_user(name, password, lastLogin, city, age, sex, icon, registerTime, nickname, mysign) values( "
			+ "#{name}, #{password}, #{lastLogin}, #{city}, #{age}, #{sex}, #{icon}, #{registerTime}, #{nickname}, #{mysign})")
	void save(UserBean user);

	@Select("select count(*)/#{pageSize} from t1_user where name like #{_username}")
	double getMaxPageByName(@Param("_username") String _username,@Param("pageSize") int pageSize);

	@Update("update t1_user set lastLogin = #{lastLogin} where id = #{id}")
	void updateUserLastLogin(@Param("id")String id, @Param("lastLogin")String lastLogin);

	@Select("SELECT * FROM t1_user WHERE id = #{id}") 
	UserBean getUserById(@Param("id")String userid);

	@Select("select * from t1_user where id in"
			+ " (select friendId from t1_relation where myid=#{userid} )")
	List<UserBean> getFriendList(@Param("userid")String userid); 
}
