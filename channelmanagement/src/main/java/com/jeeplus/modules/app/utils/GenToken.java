package com.jeeplus.modules.app.utils;

import java.util.Date;

import com.jeeplus.modules.app.tools.MD5Util;

public class GenToken {
	
	private GenToken(){}
	
	private String genToken(String uuid , String salt){
		long tmp = uuid.hashCode() ;
		long tmp2 = Math.abs(new MD5Util().md5(uuid+new Date().getTime()+tmp+salt).hashCode()) ;
//		long tmp2 = Math.abs(new MD5Util().md5(uuid+""+tmp+salt).hashCode()) ;
		return (new MD5Util().md5(tmp2+salt));
	}	
	
	
	public static String getToken(String uuid , String moblie) {
		return new GenToken().genToken(uuid, moblie);
	}
	

	
	public static void main(String[] args) throws Exception {
		System.out.println(getToken("f28a6bb7533c42e9a0c309511caadb93","18210153006"));
	}
}
