package com.mychat.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mychat.common.util.Constants;
import com.mychat.common.util.StringHelper;

public class AuthFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		HttpServletResponse httpServletResponse=(HttpServletResponse) response;
		String uri=httpServletRequest.getRequestURI();	
		//System.out.println(uri);
		//不拦截css，js，图片文件
		String newUri=uri.substring(uri.indexOf("/", 1));
		System.out.println(newUri);
		if (newUri.startsWith("resource")||newUri.startsWith("action")){
			filterChain.doFilter(request, response);
		}
		//判断是否登录
		String userid=(String) httpServletRequest.getSession().getAttribute(Constants.SESSION_USER_ID_NAME);
		if (StringHelper.isEmpty(userid)){
			httpServletRequest.getRequestDispatcher("login.html").forward(request, response);
		}else{
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

}
