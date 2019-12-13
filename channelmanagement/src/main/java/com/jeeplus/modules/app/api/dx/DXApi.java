package com.jeeplus.modules.app.api.dx;


import javax.xml.namespace.QName;



import org.apache.axis.client.Call;  
import org.apache.axis.client.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.modules.app.api.fcpp.Verify2;
import com.jeeplus.modules.app.api.product.PropertiesUtil;



import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;


public class DXApi {
	private final static Logger logger = LoggerFactory.getLogger(DXApi.class);
	
	public String callDX(String reqUrl,List requestCustInfoList) throws Exception {
		WDO2OListPortTypeProxy proxy = new WDO2OListPortTypeProxy();
		proxy.setEndpoint(reqUrl);
		WDO2OListPortType service = proxy.getWDO2OListPortType();
		CustomerInfo CustomerInfo =new CustomerInfo();
		RequestHead RequestHead = new RequestHead();
		RequestHead.setPwd(PropertiesUtil.getString("dx.pwd"));
		RequestHead.setUserId(PropertiesUtil.getString("dx.userId"));
		RequestHead.setGrade("1");
		RequestHead.setSysFlag("Y");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		RequestHead.setRequestTime(df.format(new Date()));
		RequestHead.setRequestCode("0000");
		RequestHead.setSerialNo(UUID.randomUUID().toString());
		RequestCustInfo RequestCustInfo1[] = new RequestCustInfo[requestCustInfoList.size()];
		for (int i=0;i<requestCustInfoList.size();i++){
			RequestCustInfo1[i]=(RequestCustInfo)requestCustInfoList.get(i);
		}
		
		CustomerInfo.setRequestHead(RequestHead);
		CustomerInfo.setRequestBody(RequestCustInfo1);
		com.jeeplus.modules.app.api.dx.ResponseInfo ResponseInfo = service.getWDO2OList(CustomerInfo);
		System.out.println(ResponseInfo.getResponseHead().getRetCode());
		System.out.println(ResponseInfo.getResponseHead().getRetDescript());
		return ResponseInfo.getResponseHead().getRetDescript();
	}
	
	
	
	public String preDX(List requestCustInfoList) {
		String url = PropertiesUtil.getString("dx.url");
		String result = "";
		try {
			 result = callDX(url,requestCustInfoList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		logger.info("DX参数:"+url);
		logger.info("DX结果:"+result);
		return result;
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		
		
	}
}
