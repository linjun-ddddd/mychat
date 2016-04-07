package com.mychat.web.fore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChatController {

	@RequestMapping("chat/index.html")
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return "/fore/chat/index.html";
	}
}
