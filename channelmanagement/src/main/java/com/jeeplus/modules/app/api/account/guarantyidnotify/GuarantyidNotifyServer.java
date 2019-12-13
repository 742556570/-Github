package com.jeeplus.modules.app.api.account.guarantyidnotify;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.xstream.XStreamUtils;
import com.jeeplus.modules.app.api.account.guarantyidnotify.response.GuarantyidNotifyServerResponseVO;

/**
 * 保单异步通知服务
 **@author wangjun
 **@date 2018年7月3日
 **/
public class GuarantyidNotifyServer {

	private static Logger logger = LoggerFactory.getLogger(GuarantyidNotifyServer.class);
	
	public String guarantyidNotifyServer(GuarantyidNotifyServerResponseVO guarantyidNotifyServerResponseVO){		
		// 1. 将请求vo转化为xml报文
		String resXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+XStreamUtils.convertBean2Xml(guarantyidNotifyServerResponseVO, GuarantyidNotifyServerResponseVO.class);
		// TODO 
		logger.info(resXml);
		return resXml;
	}
	
	
}
