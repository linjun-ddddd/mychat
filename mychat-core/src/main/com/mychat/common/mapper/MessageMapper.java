package com.mychat.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.mychat.bean.MessageBean;
import com.mychat.bean.UserBean;

@Component("messageMapper")
public interface MessageMapper {

	@Insert(value="insert into t1_message(data,type,toUserId,fromUserId,title,status,sendDate) "
			+ "values #{data},#{type},#{toUserId},#{fromUserId},#{title},#{status},#{sendDate}")
	void insertMessage(MessageBean message);
	
	@Update("update t1_message set status = #{status} where id = #{messageId}")
	void updateMessageStatus(@Param("messageId") String messageId,@Param("status") String status);

	@Select("select * from message where toUserId = #{toUserId} limit #{beginInde},#{pageSize}")
	List<MessageBean> getMsgByUserId(@Param("toUserId") String toUserId, @Param("beginInde") int beginInde, @Param("pageSize") int pageSize);

	
}
