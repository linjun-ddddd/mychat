package com.mychat.web.fore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.MessageBean;
import com.mychat.bean.UserBean;
import com.mychat.common.manager.MessageManager;
import com.mychat.common.manager.UserManager;
import com.mychat.common.mq.MsgQueueHelper;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class LongPollController extends BaseController{

	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	private MessageManager msgManager = (MessageManager) AppContextHelper.getInstance().getBean(MessageManager.class);
	
	
	@RequestMapping(value="longPoll.do",method=RequestMethod.POST)
	public void execute(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String userid=HttpHelper.getSessionUserid(request);
		MapMessage message=MsgQueueHelper.receiveMsg(userid);
		JSONObject returnJson = new JSONObject();
		if (message!=null){
			returnJson.put("status", Constants.STATUS_SUCCESS);
			JSONObject data = new JSONObject();
			Enumeration<String> eu=message.getMapNames();
			while (eu.hasMoreElements()) {
				String key=eu.nextElement();
				String value=message.getString(key);
				data.put(key, value);
			}
			returnJson.put("data", data);
		}else{
			returnJson.put("status", Constants.STATUS_FAIL);
		}
		packetAjaxResult(response, returnJson);
	}
	
	@RequestMapping(value="longPoll/destory.do",method=RequestMethod.POST)
	public void destory(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String userid=(String) request.getSession().getAttribute("userid");
		MsgQueueHelper.destory(userid);
		JSONObject returnJson = new JSONObject();
		returnJson.put("status", "1");
		packetAjaxResult(response, returnJson);
	}
	
	
	public static void main(String[] args) {
		MessageBean message=new MessageBean();
		message.setData("123");
		message.setFromUserId("123444");
		//{"data":"123","fromUserId":"123444"}
		System.out.println( JSONObject.toJSONString(message));
	}
}
