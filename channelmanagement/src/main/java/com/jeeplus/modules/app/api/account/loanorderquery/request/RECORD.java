package com.jeeplus.modules.app.api.account.loanorderquery.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小额信用贷款申请请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class RECORD  {

    //body
	private String CERTID;// 身份证号

	public String getCERTID() {
		return CERTID;
	}

	public void setCERTID(String cERTID) {
		CERTID = cERTID;
	}

	@Override
	public String toString() {
		return "RECORD [CERTID=" + CERTID + "]";
	}


}
