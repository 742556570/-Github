package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response;

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
	
	/**
	 * 业务状态
	 */
	@XStreamAlias("BUSISTATUS")
	private String busiStatus;
	
	/**
	 * 业务状态描述
	 */
	@XStreamAlias("BUSIDESC")
	private String busidesc;

	
	
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



	public String getBusiStatus() {
		return busiStatus;
	}



	public void setBusiStatus(String busiStatus) {
		this.busiStatus = busiStatus;
	}



	public String getBusidesc() {
		return busidesc;
	}



	public void setBusidesc(String busidesc) {
		this.busidesc = busidesc;
	}



	@Override
	public String toString() {
		return "ResponseDataBody [businessId=" + businessId + ", handleCode="
				+ handleCode + ", handleInfo=" + handleInfo + ", busiStatus="
				+ busiStatus + ", busidesc=" + busidesc + "]";
	}
	
	
}
