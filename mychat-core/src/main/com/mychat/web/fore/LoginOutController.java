package com.mychat.web.fore;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mychat.bean.UserBean;
import com.mychat.common.manager.UserManager;
import com.mychat.common.util.Constants;
import com.mychat.common.util.HttpHelper;
import com.mychat.common.util.AppContextHelper;

@Controller
public class LoginOutController extends BaseController{
	
	private UserManager userManager = (UserManager) AppContextHelper.getInstance().getBean(UserManager.class);
	
	@RequestMapping(value="loginOut.action",method= RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		JSONObject returnJson = new JSONObject();
		HttpHelper.cleanUserSession(request);
		returnJson.put("status", "1");
		packetAjaxResult(response, returnJson);
	}
}
