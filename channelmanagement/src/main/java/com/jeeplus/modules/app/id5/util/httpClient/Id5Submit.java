package com.jeeplus.modules.app.id5.util.httpClient;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.jeeplus.modules.app.id5.util.Id5RsaUtils;
import com.jeeplus.modules.app.id5.util.PropertiesUtil;
import com.jeeplus.modules.app.id5.validator.QueryValidatorServices;
import com.jeeplus.modules.app.id5.validator.QueryValidatorServicesProxy;

public class Id5Submit {
	public static void main(String[] args) {
		String param = "王XXX,621483XXXXXXXXX,15232XXXXXXXXXX,1551XXXXX";
		try {
			buildRequest(param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String buildRequest(String param) throws Exception {
		
		System.setProperty("javax.net.ssl.trustStore", "CheckID.keystore");// C CheckID.keystore：证书路径		
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();//// 用http://gboss.id5.cn/services/QueryValidatorServices?wsdl 自动生成webservice 的代理对象
		proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices?wsdl");
		QueryValidatorServices service = proxy.getQueryValidatorServices();
		String desKey = PropertiesUtil.getString("desKey");
		if(StringUtils.isBlank(desKey)){
			desKey="12345678";
		}
		String userName_encode = Id5RsaUtils.encode(desKey, PropertiesUtil.getString("userName"));			// 用户名
		String password_encode = Id5RsaUtils.encode(desKey, PropertiesUtil.getString("password"));			// 密码
		String param_encode  = Id5RsaUtils.encode(desKey, param);					// 参数
		String datasource_encode = Id5RsaUtils.encode(desKey,  PropertiesUtil.getString("dataType"));	// 数据类型
		String resultXML = "";
		// 单条
		try {
			resultXML = service.querySingle(userName_encode, password_encode, datasource_encode, param_encode);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return Id5RsaUtils.decodeValue(desKey, resultXML);
	}

}
