package com.mychat.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mychat.common.manager.UserManager;
import com.mychat.common.mapper.UserMapper;

public class AppContextHelper implements ApplicationContextAware {

	ApplicationContext applicationContext;
	
	static AppContextHelper springHelper ;
	
	private AppContextHelper () {}
	
	public static AppContextHelper getInstance() {
		return springHelper;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		springHelper=this;
		this.applicationContext=applicationContext;
	}

	@SuppressWarnings("unchecked")
	public Object getBean(Class clazz) {
		// TODO Auto-generated method stub
		return applicationContext.getBean(clazz);
	}

	public Object getBean(String beanName) {
		// TODO Auto-generated method stub
		return applicationContext.getBean(beanName);
	}
	
}
