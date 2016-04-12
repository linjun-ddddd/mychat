package com.mychat.common.manager;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mychat.bean.RealationBean;
import com.mychat.common.mapper.MessageMapper;
import com.mychat.common.mapper.RelationMapper;
import com.mychat.common.util.AppContextHelper;

@Scope("singleton")
@Component
public class RelationManager {
	RelationMapper relationMapper=(RelationMapper) AppContextHelper.getInstance().getBean("relationMapper");
	
	public List<RealationBean> getLastChatFriend(String userid){
		int amount=25;
		return relationMapper.getLastChatRelation(userid, amount);
	}

	public void saveRelation(String userId, String fromUserId) {
		// TODO Auto-generated method stub
		relationMapper.saveRelation(userId, fromUserId);
		relationMapper.saveRelation(fromUserId,userId);
	}
}
