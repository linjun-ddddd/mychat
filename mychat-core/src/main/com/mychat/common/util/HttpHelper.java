package com.mychat.common.util;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {

	public static String getSessionUserid(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return (String) request.getSession().getAttribute("userid");
	}

	public static void setSessionUserid(HttpServletRequest request, String userid) {
		// TODO Auto-generated method stub
		request.getSession(true).setAttribute("userid", userid);
	}

	public static void cleanUserSession(HttpServletRequest request) {
		// TODO Auto-generated method stub
		request.getSession(true).removeAttribute("userid");
	}

}
