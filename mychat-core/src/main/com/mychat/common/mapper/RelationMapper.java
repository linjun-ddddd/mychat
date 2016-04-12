package com.mychat.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.mychat.bean.RealationBean;

@Component("relationMapper")
public interface RelationMapper {
	
	@Select("select * from t1_relation where myid=#{userid} order by lastChatDate desc limit #{amount};")
	List<RealationBean> getLastChatRelation(@Param("userid")String userid,@Param("amount") int amount);

	@Insert("insert into t1_relation(myid,friendId) values(#{myid},#{friendId})")
	void saveRelation(@Param("myid")String userId, @Param("friendId")String fromUserId);

	@Select("select count(*)!=0 from t1_relation where myid=#{myid} and friendId = #{friendId}")
	String checkIsFriend(@Param("myid")String myid, @Param("friendId")String friendId);
}
