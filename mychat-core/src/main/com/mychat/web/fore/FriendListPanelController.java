package com.mychat.web.fore;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.MessageBean;
import com.mychat.bean.UserBean;
import com.mychat.common.manager.MessageManager;
import com.mychat.common.manager.RelationManager;
import com.mychat.common.manager.UserManager;
import com.mychat.common.mq.MsgQueueHelper;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class FriendListPanelController extends BaseController{

	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	private MessageManager msgManager = (MessageManager) AppContextHelper.getInstance().getBean(MessageManager.class);
	private RelationManager relationManager = (RelationManager) AppContextHelper.getInstance().getBean(RelationManager.class);
	
	@RequestMapping(value="friend/list.do",method=RequestMethod.POST)
	public void search(HttpServletRequest request,HttpServletResponse response){
		JSONObject returnJson = new JSONObject();
		String userid = HttpHelper.getSessionUserid(request);
		//System.out.println("search param:"+_username+" | "+page);
		
		List<UserBean> userList = userManager.getFriendList(userid);
		//返回ajax结果
		returnJson.put("status", "1");
		returnJson.put("data", JSONArray.toJSON(userList));
		packetAjaxResult(response,returnJson);
		
	}
	

}
