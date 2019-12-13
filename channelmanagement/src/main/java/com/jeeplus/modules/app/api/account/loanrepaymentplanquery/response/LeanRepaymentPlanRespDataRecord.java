package com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LeanRepaymentPlanRespDataRecord {

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
	
	@XStreamAlias("CONTENT")
	private String repaymentDetails;

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

	public String getRepaymentDetails() {
		return repaymentDetails;
	}

	public void setRepaymentDetails(String repaymentDetails) {
		this.repaymentDetails = repaymentDetails;
	}

	@Override
	public String toString() {
		return "LeadRepaymentPlanRespDataRecord [businessId=" + businessId
				+ ", handleCode=" + handleCode + ", handleInfo=" + handleInfo
				+ ", repaymentDetails=" + repaymentDetails + "]";
	}
	
	
}
