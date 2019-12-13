package com.jeeplus.common.xstream;

import com.thoughtworks.xstream.XStream;

public abstract class XStreamUtils {

	
	/**
	 * 将xml对象转为java对象
	 * @param xmlStr
	 * @param classType
	 * @return
	 */
	public static <T> T covertXml2JavaBean(String xmlStr,Class<T> classType){
		XStream xstream = new XStream();
		xstream.processAnnotations(classType);
		
		return (T)xstream.fromXML(xmlStr);
	}
	
	public static <T> String convertBean2Xml(Object obj,Class<T> classType){
		XStream xstream = new XStream();
		xstream.processAnnotations(classType);
		return xstream.toXML(obj);
	}
	 
}
