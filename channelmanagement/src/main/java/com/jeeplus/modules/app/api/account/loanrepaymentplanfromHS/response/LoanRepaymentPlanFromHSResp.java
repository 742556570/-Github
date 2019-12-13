package com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.response;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("msgall")
public class LoanRepaymentPlanFromHSResp {
	// 成功标识
	@XStreamAlias("errorCode")
	
	private String errorCode;
	// 错误原因
	@XStreamAlias("errorMsg")
	private String errorMsg;
	// 响应时间
	@XStreamAlias("sendTime")
	private String sendTime;
	// 保单号
	@XStreamAlias("guarantyId")
	private String guarantyId;
	// 借据号
	@XStreamAlias("billNo")
	private String billNo;
	// 贷款金额
	@XStreamAlias("loanAmt")
	private String loanAmt;
	// 贷款期数
	@XStreamAlias("tnr")
	private String tnr;
	// 起始日期
	@XStreamAlias("startDate")
	private String startDate;
	// 到期日期
	@XStreamAlias("endDate")
	private String endDate;
	// 剩余本金
	@XStreamAlias("remainCapital")
	private String remainCapital;
	// 账务状态
	@XStreamAlias("loanDebtSts")
	private String loanDebtSts;

	@XStreamAlias("PayShdList")
	private ArrayList<LoanRepaymentPlanFromHSDataBody> dataBodyList;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getGuarantyId() {
		return guarantyId;
	}

	public void setGuarantyId(String guarantyId) {
		this.guarantyId = guarantyId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getTnr() {
		return tnr;
	}

	public void setTnr(String tnr) {
		this.tnr = tnr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemainCapital() {
		return remainCapital;
	}

	public void setRemainCapital(String remainCapital) {
		this.remainCapital = remainCapital;
	}

	public String getLoanDebtSts() {
		return loanDebtSts;
	}

	public void setLoanDebtSts(String loanDebtSts) {
		this.loanDebtSts = loanDebtSts;
	}

	public ArrayList<LoanRepaymentPlanFromHSDataBody> getDataBodyList() {
		return dataBodyList;
	}

	public void setDataBodyList(ArrayList<LoanRepaymentPlanFromHSDataBody> dataBodyList) {
		this.dataBodyList = dataBodyList;
	}

	@Override
	public String toString() {
		return "LoanRepaymentPlanFromHSResp [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", sendTime="
				+ sendTime + ", guarantyId=" + guarantyId + ", billNo=" + billNo + ", loanAmt=" + loanAmt + ", tnr="
				+ tnr + ", startDate=" + startDate + ", endDate=" + endDate + ", remainCapital=" + remainCapital
				+ ", loanDebtSts=" + loanDebtSts + ", dataBodyList=" + dataBodyList + "]";
	}

}
