package com.jeeplus.modules.app.api.account.CreditLoanApply.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CreditLoanApplyRespDataRecord {

	/**
	 * 业务保单号
	 */
	@XStreamAlias("BUSINESSID")
	private String businessId;
	
	/**
	 * 处理编码
	 */
	@XStreamAlias("HANDLECODE")
	private String handleCode;
	
	/**
	 * 业务处理描述
	 */
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
		return "CreditLoanApplyRespDataRecord [businessId=" + businessId
				+ ", handleCode=" + handleCode + ", handleInfo=" + handleInfo
				+ "]";
	}
	
	
}
