package com.mychat.web.fore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.MessageBean;
import com.mychat.bean.RealationBean;
import com.mychat.bean.UserBean;
import com.mychat.common.manager.MessageManager;
import com.mychat.common.manager.RelationManager;
import com.mychat.common.manager.UserManager;
import com.mychat.common.mq.izchat.MsgQueueHelper;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class ChatController extends BaseController{

	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	private MessageManager msgManager = (MessageManager) AppContextHelper.getInstance().getBean(MessageManager.class);
	private RelationManager relationManager = (RelationManager) AppContextHelper.getInstance().getBean(RelationManager.class);
	
	
	@RequestMapping("chat/index.html")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String userid=HttpHelper.getSessionUserid(request);
		UserBean user=userManager.getUserById(userid);
		//获取最近聊天列表
		List<RealationBean> lastChatRelation = relationManager.getLastChatFriend(userid);
		List<UserBean> lastChatUser = new ArrayList<>();
		for (int i=0;i<lastChatRelation.size();i++){
			String friendId = lastChatRelation.get(i).getFriendId();
			UserBean userBean = userManager.getUserById(friendId);
			lastChatUser.add(userBean);
		}
		//获取性别
		if (user.getSex()==null){
			user.setSex("未知");
		}else{
			if ("1".equals(user.getSex())){
				user.setSex("男");
			}else if ("2".equals(user.getSex())){
				user.setSex("女");
			}
		}

		request.setAttribute("user", user);
		request.setAttribute("lastChatUser", lastChatUser);
		return "/fore/chat/index.html";
	}
	
	

	@RequestMapping(value="chat/send.do",method=RequestMethod.POST)
	public void send(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String userid=HttpHelper.getSessionUserid(request);
		String toUserId=request.getParameter("toUserId");
		String msgData=request.getParameter("msgData");
		//更新聊天时间
		relationManager.updateLastChatDate(toUserId,userid);
		
		//打包消息
		MessageBean message = new MessageBean();
		message.setFromUserId(userid);
		message.setToUserId(toUserId);
		message.setData(msgData);
		message.setSendDate(new Timestamp(System.currentTimeMillis()).toString());
		message.setType(Constants.CHAT_MESSAGE_TYPE);
		MsgQueueHelper.sendMessage(message);
		msgManager.saveMessage(message);
		
		//返回ajax结果
		JSONObject returnJson = new JSONObject();
		returnJson.put("status", Constants.STATUS_SUCCESS);
		returnJson.put("sendDate", message.getSendDate());
		returnJson.put("message", JSONObject.toJSON(message));
		packetAjaxResult(response, returnJson);
	
	}
	
	@RequestMapping(value="chat/getHistory.do",method=RequestMethod.POST)
	public void getHistory(HttpServletRequest request, HttpServletResponse response) throws JMSException {
		String userid=HttpHelper.getSessionUserid(request);
		String friendId=request.getParameter("friendId");
		
		final int hisAmount = 16;
		List<MessageBean> messageList=msgManager.getHistoryChatMsg(hisAmount,userid,friendId);
		//返回ajax结果
		JSONObject returnJson = new JSONObject();
		returnJson.put("status", Constants.STATUS_SUCCESS);
		returnJson.put("messageList", JSONObject.toJSON(messageList));
		packetAjaxResult(response, returnJson);
	
	}
	
}
