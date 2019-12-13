package com.jeeplus.modules.app.api.account.bankcard.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月3日
 **/
public class BankCardResponseRecord {

	@XStreamAlias("BUSINESSID")
	private String businessId;
	@XStreamAlias("HANDLECODE")
	private String handleCode;
	@XStreamAlias("HANDLEINFO")
	private String handleInfo;
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getHandleCode() {
		return handleCode;
	}
	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}
	public String getHandleInfo() {
		return handleInfo;
	}
	public void setHandleInfo(String handleInfo) {
		this.handleInfo = handleInfo;
	}
	@Override
	public String toString() {
		return "BankCardResponseRecord [businessId=" + businessId
				+ ", handleCode=" + handleCode + ", handleInfo=" + handleInfo
				+ "]";
	}
	
	
	
}
