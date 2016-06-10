package com.mychat.mybatisTest;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;

import javax.sql.DataSource;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;

import com.mychat.bean.UserBean;
import com.mychat.common.util.CommonHelper;

public class Main {
	public static void main2(String[] args) throws IOException {
		Reader reader = Resources.getResourceAsReader("com/mychat/mybatisTest/test-mybatis.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		SqlSession session = sqlSessionFactory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);

		for (int i = 0; i < 1000; i++) {
			UserBean user = new UserBean();
			user.setAge(String.valueOf(RandomUtils.nextInt(45) + 15));
			user.setCity(RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(5) + 5));
			user.setIcon(String.valueOf(RandomUtils.nextInt(9) + 1) + ".png");
			user.setMysign(RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(200) + 46));
			user.setName(RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(30) + 10));
			user.setNickname(RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(30) + 10));
			user.setPassword(CommonHelper.md5("123"));
			user.setRegisterTime(new Timestamp(System.currentTimeMillis() - RandomUtils.nextInt(3600 * 24 * 30)).toString());
			userMapper.save(user);
		}
		session.commit();
		session.close();
	}

	public Main() {
		// TODO Auto-generated constructor stub
		System.out.println("Main");
	}
	
	public static void main(String[] args) {
		new Main() {
			{
				Hello("123");
			}
		}.Hello("111");

		// new SQL(){
		// {
		// SELECT("");
		// }
		// };
	}

	public void Hello(String str) {
		// TODO Auto-generated constructor stub
		System.out.println(str);
	}
}