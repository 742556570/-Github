package com.jeeplus.modules.app.api.sms;

import java.util.HashMap;
import java.util.Map;

public class Sms253Util {

	public boolean sendRgsCde(String tel, String content) {
		String url = "http://10.7.128.33:9081/sms/ygbx/sendSms.html";
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("phone", tel);
		params.put("type", 1+"");
		params.put("code", content);
		String result = new HttpConUtils().doPostParamsMap(url, params);
		if(result.contains("success")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new Sms253Util().sendRgsCde("18210153006", "123"));
	}
}
