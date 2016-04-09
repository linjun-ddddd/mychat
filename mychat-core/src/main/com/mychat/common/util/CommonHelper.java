package com.mychat.common.util;

import java.security.MessageDigest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class CommonHelper {

	public static String md5(String password) {
		// TODO Auto-generated method stub
		MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            e.printStackTrace();  
            return "";  
        }  
        byte[] md5Bytes = md5.digest(password.getBytes());  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
	}
	
//	public static String randomStr(int length) {
//		StringBuilder str = new StringBuilder();
//		
//		for (int i=0;i<length;i++){
//			str.append(str)
//		}
//	}
	
	public static void main(String[] args) {
		System.out.println(RandomStringUtils.randomAlphanumeric((13)));
		System.out.println(RandomStringUtils.randomAlphabetic((13)));
		//System.out.println(RandomUtils.nextInt(30)+10);
	}
}
