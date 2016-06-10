package com.mychat.bean.generator;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.mychat.bean.MessageBean;
import com.mychat.bean.UserBean;
import com.mychat.common.mapper.MessageMapper;
import com.mychat.common.mapper.UserMapper;
import com.mychat.common.util.CommonHelper;
import com.mychat.common.util.Constants;

public class MessageGenerator {

	public static void main(String[] args) throws IOException {
		Reader reader = Resources.getResourceAsReader("com/mychat/bean/generator/test-mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		SqlSession session = sqlSessionFactory.openSession();
		MessageMapper messageMapper = session.getMapper(MessageMapper.class);
		UserMapper userMapper = session.getMapper(UserMapper.class);

		int minUserId = 26140;
		int maxUserId = 27142;
		//遍历所有用户
		for (int i = minUserId; i <= maxUserId; i++) {
			List<UserBean> userList = userMapper.getFriendList(i+"");
			System.out.println("pecetage: "+((i-minUserId)*1.0f/(maxUserId-minUserId)*100.0)+" %");
			//遍历所有好友
			for (int tot=0;tot<userList.size();tot++){
				int msgAccount = RandomUtils.nextInt(1)+5;
				UserBean curUser = userList.get(tot);
				//每个好友发送msgAccount条数据
				for (int j=0;j<msgAccount;j++){
					MessageBean messageBean = new MessageBean();
					messageBean.setType(Constants.CHAT_MESSAGE_TYPE);
					messageBean.setSendDate(new Timestamp(System.currentTimeMillis()-RandomUtils.nextInt(3600*24*30)-3600).toString());
					messageBean.setData(RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(60)+8));
					if (RandomUtils.nextBoolean()){
						messageBean.setToUserId(i+"");
						messageBean.setFromUserId(curUser.getId());
					}else{
						messageBean.setToUserId(curUser.getId());
						messageBean.setFromUserId(i+"");
					}
					
					messageMapper.insertMessage(messageBean);
				}
			}
			
			
		}
		session.commit();
		session.close();
	}
}
