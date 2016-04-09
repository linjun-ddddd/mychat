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
import com.mychat.common.manager.UserManager;
import com.mychat.common.mq.MsgQueueHelper;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class MsgBoxPanelController extends BaseController{

	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	private MessageManager msgManager = (MessageManager) AppContextHelper.getInstance().getBean(MessageManager.class);
	
	@RequestMapping(value="msgBox/getMessage.do",method=RequestMethod.POST)
	public void getMessage(HttpServletRequest request,HttpServletResponse response){
		JSONObject returnJson = new JSONObject();
		String toUserId = HttpHelper.getSessionUserid(request);
		String page= request.getParameter("page");
		//System.out.println("search param:"+_username+" | "+page);
		
		List<MessageBean> messageList = msgManager.getMsgByUserId(toUserId,page);
		
		//返回ajax结果
		returnJson.put("status", Constants.STATUS_SUCCESS);
		returnJson.put("data", JSONArray.toJSON(messageList));
		packetAjaxResult(response,returnJson);
		
	}
	

	@RequestMapping(value="msgBox/agreeMessage.do",method=RequestMethod.POST)
	public void agreeMessage(HttpServletRequest request,HttpServletResponse response) throws JMSException{
		JSONObject returnJson = new JSONObject();
		String userId = HttpHelper.getSessionUserid(request) ;
		String fromUserId = request.getParameter("fromUserId");
		String messageId = request.getParameter("messageId");
		//更新消息状态
		msgManager.updateMessageStatus(messageId,Constants.MESSAGE_DEAL_AGREE_STATUS);
		
		//打包同意message
		UserBean user=userManager.getUserById(userId);
		MessageBean agreeMessage = new MessageBean();
		agreeMessage.setData(user.getName()+" 同意了您的添加好友请求");
		agreeMessage.setFromUserId(userId);
		agreeMessage.setToUserId(fromUserId);
		agreeMessage.setType(Constants.ADD_FRIEND_SUCCESS_MESSAGE_TYPE);
		MsgQueueHelper.sendMessage(agreeMessage);
		msgManager.saveMessage(agreeMessage);
		
		//打包两个更新好友列表message
		MessageBean message1 = new MessageBean();
		message1.setFromUserId(userId);
		message1.setToUserId(fromUserId);
		message1.setType(Constants.UPDATE_FRIEND_LIST_MESSAGE_TYPE);
		MsgQueueHelper.sendMessage(message1);
		msgManager.saveMessage(message1);
		
		
		MessageBean message2 = new MessageBean();
		message2.setFromUserId(fromUserId);
		message2.setToUserId(userId);
		message2.setType(Constants.UPDATE_FRIEND_LIST_MESSAGE_TYPE);
		MsgQueueHelper.sendMessage(message2);
		msgManager.saveMessage(message2);
		
		//返回ajax结果
		returnJson.put("status", Constants.STATUS_SUCCESS);
		packetAjaxResult(response,returnJson);
	}
	
	@RequestMapping(value="msgBox/rejectMessage.do",method=RequestMethod.POST)
	public void rejectMessage(HttpServletRequest request,HttpServletResponse response) throws JMSException{
		JSONObject returnJson = new JSONObject();
		String userId = HttpHelper.getSessionUserid(request) ;
		String fromUserId = request.getParameter("fromUserId");
		String messageId = request.getParameter("messageId");
		//更新消息状态
		msgManager.updateMessageStatus(messageId,Constants.MESSAGE_DEAL_REJECT_STATUS);
		
		//打包同意message
		UserBean user=userManager.getUserById(userId);
		MessageBean agreeMessage = new MessageBean();
		agreeMessage.setData(user.getName()+" 拒绝了您的添加好友请求");
		agreeMessage.setFromUserId(userId);
		agreeMessage.setToUserId(fromUserId);
		agreeMessage.setType(Constants.ADD_FRIEND_SUCCESS_MESSAGE_TYPE);
		MsgQueueHelper.sendMessage(agreeMessage);
		msgManager.saveMessage(agreeMessage);
		
		//返回ajax结果
		returnJson.put("status", Constants.STATUS_SUCCESS);
		packetAjaxResult(response,returnJson);
	}
}
