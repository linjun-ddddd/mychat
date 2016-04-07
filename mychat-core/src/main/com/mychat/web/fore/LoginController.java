package com.mychat.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mychat.bean.UserBean;
import com.mychat.common.manager.UserManager;
import com.mychat.common.util.Constants;
import com.mychat.common.util.SpringHelper;

@Controller
public class LoginController{
	
	private UserManager userManager = (UserManager) SpringHelper.getInstance().getBean(UserManager.class);
	
	@RequestMapping("/login.html")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "/fore/login/index.html";
	}
	
	@RequestMapping(value="login.action",method= RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserBean user=userManager.getUser(username,password);
		if (user!=null){
			request.getSession().setAttribute(Constants.SESSION_USER_ID_NAME, user.getId());
			return "redirect:chat/index.html";
		}else{
			request.setAttribute("errorMsg", "登录失败。。");
			request.setAttribute("request", request);
			return "/fore/login/index.html";
		}
	}
}
