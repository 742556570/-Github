package com.jeeplus.modules.app.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.xml.XMLSerializer;

public class JSONXMLUtils {  
  
	public JSONObject xml2JSONObject (String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();  
		 //将xml转为json（注：如果是元素的属性，会在json里的key前加一个@标识）  
		 String result = xmlSerializer.read(xml).toString();  
		 //输出json内容
		 JSONObject json = JSONObject.parseObject(result);
		 return json;
	}


}  
