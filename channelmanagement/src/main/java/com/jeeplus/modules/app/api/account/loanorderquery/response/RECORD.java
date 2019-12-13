package com.jeeplus.modules.app.api.account.loanorderquery.response;

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
	private String HANDLECODE;// CODE
	private String HANDLEINFO;// message
	private String CONTENT;// 详情

	public String getCERTID() {
		return CERTID;
	}

	public void setCERTID(String cERTID) {
		CERTID = cERTID;
	}

	public String getHANDLECODE() {
		return HANDLECODE;
	}

	public void setHANDLECODE(String hANDLECODE) {
		HANDLECODE = hANDLECODE;
	}

	public String getHANDLEINFO() {
		return HANDLEINFO;
	}

	public void setHANDLEINFO(String hANDLEINFO) {
		HANDLEINFO = hANDLEINFO;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	@Override
	public String toString() {
		return "RECORD [CERTID=" + CERTID + "]";
	}


}
