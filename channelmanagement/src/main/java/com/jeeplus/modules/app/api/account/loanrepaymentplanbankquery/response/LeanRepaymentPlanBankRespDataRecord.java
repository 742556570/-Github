package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LeanRepaymentPlanBankRespDataRecord {

	/**
	 * 业务保单号
	 */
	@XStreamAlias("GUARANTYID")
	private String guarantyid;
	/**
	 * 借据号
	 */
	@XStreamAlias("LOANACNO")
	private String loanAcNo;

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
	 * 贷款金额
	 */
	@XStreamAlias("LOANAMT")
	private String loanAmt;

	/**
	 * 贷款期数
	 */
	@XStreamAlias("LOANTERM")
	private String loanTerm;

	/**
	 * 还款方式
	 */
	@XStreamAlias("REPAYMENT")
	private String rePayMent;

	/**
	 * 起始日期
	 */
	@XStreamAlias("STARTDATE")
	private String startDate;
	/**
	 * 到期日期
	 */
	@XStreamAlias("EXPIRYDATE")
	private String expiryDate;
	/**
	 * 剩余本金
	 */
	@XStreamAlias("REMAINCAPI")
	private String remainCapi;
	/**
	 * 账户状态
	 */
	@XStreamAlias("ACCOUNTSTATUS")
	private String accountStatus;

	@XStreamAlias("CONTENT")
	private String repaymentBankDetails;

	public String getGuarantyid() {
		return guarantyid;
	}

	public void setGuarantyid(String guarantyid) {
		this.guarantyid = guarantyid;
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

	public String getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getRePayMent() {
		return rePayMent;
	}

	public void setRePayMent(String rePayMent) {
		this.rePayMent = rePayMent;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRemainCapi() {
		return remainCapi;
	}

	public void setRemainCapi(String remainCapi) {
		this.remainCapi = remainCapi;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getLoanAcNo() {
		return loanAcNo;
	}

	public void setLoanAcNo(String loanAcNo) {
		this.loanAcNo = loanAcNo;
	}

	public String getRepaymentBankDetails() {
		return repaymentBankDetails;
	}

	public void setRepaymentBankDetails(String repaymentBankDetails) {
		this.repaymentBankDetails = repaymentBankDetails;
	}

	@Override
	public String toString() {
		return "LeanRepaymentPlanBankRespDataRecord [guarantyid=" + guarantyid + ", loanAcNo=" + loanAcNo
				+ ", handleCode=" + handleCode + ", handleInfo=" + handleInfo + ", loanAmt=" + loanAmt + ", loanTerm="
				+ loanTerm + ", rePayMent=" + rePayMent + ", startDate=" + startDate + ", expiryDate=" + expiryDate
				+ ", remainCapi=" + remainCapi + ", accountStatus=" + accountStatus + ", repaymentBankDetails="
				+ repaymentBankDetails + "]";
	}

}
