package com.mychat.web.fore;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class BaseController {

	
	protected void packetAjaxResult(HttpServletResponse response, JSONObject returnJson) {
		// TODO Auto-generated method stub
		System.out.println("ajax result:"+returnJson);
		try {
			PrintWriter writer = response.getWriter();
			writer.write(returnJson.toJSONString());
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
