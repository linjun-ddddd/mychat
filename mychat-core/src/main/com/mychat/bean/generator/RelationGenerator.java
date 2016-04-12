package com.mychat.bean.generator;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mychat.bean.RealationBean;
import com.mychat.bean.UserBean;
import com.mychat.common.mapper.RelationMapper;
import com.mychat.common.util.CommonHelper;
import com.mychat.mybatisTest.UserMapper;

public class RelationGenerator {

	public static void main(String[] args) throws IOException {
		Reader reader = Resources.getResourceAsReader("com/mychat/bean/generator/test-mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		

		int minUserId = 26140;
		int maxUserId = 27142;
		for (int i = minUserId; i <= maxUserId; i++) {
			SqlSession session = sqlSessionFactory.openSession();
			RelationMapper relationMapper = session.getMapper(RelationMapper.class);
			//
			String myid=i+"";
			int j=minUserId;
			while (true){
				j+=RandomUtils.nextInt(50)+1;
				if (j>maxUserId) break;
				String friendId = j+"";
				if ("0".equals(relationMapper.checkIsFriend(myid,friendId))){
					relationMapper.saveRelation(myid,friendId);
					relationMapper.saveRelation(friendId,myid);
				}else{
					//System.out.println(myid+" : "+friendId);
				}
				
			}
			//
			session.commit();
			session.close();
		}
		
	}
}
