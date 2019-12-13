package com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.response;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class LoanRepaymentPlanFromHSMX  extends DataEntity<LoanRepaymentPlanFromHSMX> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 应还期次
	private String perdNo;
	// 本期应还日期
	private String dueDt;
	// 应还本金
	private BigDecimal normPrcpAmt;
	// 应还利息
	private BigDecimal normIntAmt;
	// 应还罚息
	private BigDecimal odIntAmt;
	// 应还保费
	private BigDecimal holdFeeAmt;
	// 应还服务费
	private BigDecimal serviceFee;
	// 实还本金
	private BigDecimal setlPrcp;
	// 实还利息
	private BigDecimal setlNormInt;
	// 实还罚息
	private BigDecimal setlOdIntAmt;
	// 实还保费
	private BigDecimal setlFeeAmt;
	// 实还服务费
	private BigDecimal setlServiceFee;
	// 拖欠标识
	private String psOdInd;
	// 结清标识
	private String setlInd;
	// 结清日期
	private String setlDate;
	// 提前结清应还违约金
	private BigDecimal prePayFeeAmt;
	// 提前结清实还违约金
	private BigDecimal setlPrePayFeeAmt;
	// 追偿应还违约金
	private BigDecimal subrogationFeeAmt;
	// 追偿实还违约金
	private BigDecimal setlSubrogationFeeAmt;

	public String getPerdNo() {
		return perdNo;
	}

	public void setPerdNo(String perdNo) {
		this.perdNo = perdNo;
	}

	public String getDueDt() {
		return dueDt;
	}

	public void setDueDt(String dueDt) {
		this.dueDt = dueDt;
	}

	public BigDecimal getNormPrcpAmt() {
		return normPrcpAmt;
	}

	public void setNormPrcpAmt(BigDecimal normPrcpAmt) {
		this.normPrcpAmt = normPrcpAmt;
	}

	public BigDecimal getNormIntAmt() {
		return normIntAmt;
	}

	public void setNormIntAmt(BigDecimal normIntAmt) {
		this.normIntAmt = normIntAmt;
	}

	public BigDecimal getOdIntAmt() {
		return odIntAmt;
	}

	public void setOdIntAmt(BigDecimal odIntAmt) {
		this.odIntAmt = odIntAmt;
	}

	public BigDecimal getHoldFeeAmt() {
		return holdFeeAmt;
	}

	public void setHoldFeeAmt(BigDecimal holdFeeAmt) {
		this.holdFeeAmt = holdFeeAmt;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getSetlPrcp() {
		return setlPrcp;
	}

	public void setSetlPrcp(BigDecimal setlPrcp) {
		this.setlPrcp = setlPrcp;
	}

	public BigDecimal getSetlNormInt() {
		return setlNormInt;
	}

	public void setSetlNormInt(BigDecimal setlNormInt) {
		this.setlNormInt = setlNormInt;
	}

	public BigDecimal getSetlOdIntAmt() {
		return setlOdIntAmt;
	}

	public void setSetlOdIntAmt(BigDecimal setlOdIntAmt) {
		this.setlOdIntAmt = setlOdIntAmt;
	}

	public BigDecimal getSetlFeeAmt() {
		return setlFeeAmt;
	}

	public void setSetlFeeAmt(BigDecimal setlFeeAmt) {
		this.setlFeeAmt = setlFeeAmt;
	}

	public BigDecimal getSetlServiceFee() {
		return setlServiceFee;
	}

	public void setSetlServiceFee(BigDecimal setlServiceFee) {
		this.setlServiceFee = setlServiceFee;
	}

	public String getPsOdInd() {
		return psOdInd;
	}

	public void setPsOdInd(String psOdInd) {
		this.psOdInd = psOdInd;
	}

	public String getSetlInd() {
		return setlInd;
	}

	public void setSetlInd(String setlInd) {
		this.setlInd = setlInd;
	}

	public String getSetlDate() {
		return setlDate;
	}

	public void setSetlDate(String setlDate) {
		this.setlDate = setlDate;
	}

	public BigDecimal getPrePayFeeAmt() {
		return prePayFeeAmt;
	}

	public void setPrePayFeeAmt(BigDecimal prePayFeeAmt) {
		this.prePayFeeAmt = prePayFeeAmt;
	}

	public BigDecimal getSetlPrePayFeeAmt() {
		return setlPrePayFeeAmt;
	}

	public void setSetlPrePayFeeAmt(BigDecimal setlPrePayFeeAmt) {
		this.setlPrePayFeeAmt = setlPrePayFeeAmt;
	}

	public BigDecimal getSubrogationFeeAmt() {
		return subrogationFeeAmt;
	}

	public void setSubrogationFeeAmt(BigDecimal subrogationFeeAmt) {
		this.subrogationFeeAmt = subrogationFeeAmt;
	}

	public BigDecimal getSetlSubrogationFeeAmt() {
		return setlSubrogationFeeAmt;
	}

	public void setSetlSubrogationFeeAmt(BigDecimal setlSubrogationFeeAmt) {
		this.setlSubrogationFeeAmt = setlSubrogationFeeAmt;
	}

	@Override
	public String toString() {
		return "LoanRepaymentPlanBankDetail [perdNo=" + perdNo + ", dueDt=" + dueDt + ", normPrcpAmt=" + normPrcpAmt
				+ ", normIntAmt=" + normIntAmt + ", odIntAmt=" + odIntAmt + ", holdFeeAmt=" + holdFeeAmt
				+ ", serviceFee=" + serviceFee + ", setlPrcp=" + setlPrcp + ", setlNormInt=" + setlNormInt
				+ ", setlOdIntAmt=" + setlOdIntAmt + ", setlFeeAmt=" + setlFeeAmt + ", setlServiceFee=" + setlServiceFee
				+ ", psOdInd=" + psOdInd + ", setlInd=" + setlInd + ", setlDate=" + setlDate + ", prePayFeeAmt="
				+ prePayFeeAmt + ", setlPrePayFeeAmt=" + setlPrePayFeeAmt + ", subrogationFeeAmt=" + subrogationFeeAmt
				+ ", setlSubrogationFeeAmt=" + setlSubrogationFeeAmt + "]";
	}
}
