package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;
import com.jeeplus.common.persistence.DataEntity;

public class LoanRepaymentPlanBankDetail extends DataEntity<LoanRepaymentPlanBankDetail> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 保单号
	private String guarantyid;
	// 借据号
	@SerializedName("LOANACNO")
	private String loanacno;
	// 拖欠标识
	private String overflag;
	// 结清日期
	private String payoffdate;
	// 结清标识
	private String payoffflag;
	// 实还本金
	private BigDecimal rcapi;
	// 实还服务费
	private BigDecimal rcontamt;
	// 实还罚息
	private BigDecimal rfine;
	// 实还保费
	private BigDecimal rinsuamt;
	// 实还利息
	private BigDecimal rinte;
	// 应还本金
	private BigDecimal scapi;
	// 应还服务费
	private BigDecimal scontamt;
	// 本期应还日期
	private String sdate;
	// 应还罚息
	private BigDecimal sfine;
	// 应还保费
	private BigDecimal sinsuamt;
	// 应还利息
	private BigDecimal sinte;
	// 还款期次
	private String sterm;
	// 提前结清应还违约金
	private String sPayLiqDam;
	// 提前结清实还违约金
	private String rPayLiqDam;
	// 追偿应还违约金
	private String sRecPayLiqDam;
	// 追偿实还违约金
	private String rRecPayLiqDam;

	public String getGuarantyid() {
		return guarantyid;
	}

	public void setGuarantyid(String guarantyid) {
		this.guarantyid = guarantyid;
	}

	public String getLoanacno() {
		return loanacno;
	}

	public void setLoanacno(String loanacno) {
		this.loanacno = loanacno;
	}

	public String getOverflag() {
		return overflag;
	}

	public void setOverflag(String overflag) {
		this.overflag = overflag;
	}

	public String getPayoffdate() {
		return payoffdate;
	}

	public void setPayoffdate(String payoffdate) {
		this.payoffdate = payoffdate;
	}

	public String getPayoffflag() {
		return payoffflag;
	}

	public void setPayoffflag(String payoffflag) {
		this.payoffflag = payoffflag;
	}

	public BigDecimal getRcapi() {
		return rcapi;
	}

	public void setRcapi(BigDecimal rcapi) {
		this.rcapi = rcapi;
	}

	public BigDecimal getRcontamt() {
		return rcontamt;
	}

	public void setRcontamt(BigDecimal rcontamt) {
		this.rcontamt = rcontamt;
	}

	public BigDecimal getRfine() {
		return rfine;
	}

	public void setRfine(BigDecimal rfine) {
		this.rfine = rfine;
	}

	public BigDecimal getRinsuamt() {
		return rinsuamt;
	}

	public void setRinsuamt(BigDecimal rinsuamt) {
		this.rinsuamt = rinsuamt;
	}

	public BigDecimal getRinte() {
		return rinte;
	}

	public void setRinte(BigDecimal rinte) {
		this.rinte = rinte;
	}

	public BigDecimal getScapi() {
		return scapi;
	}

	public void setScapi(BigDecimal scapi) {
		this.scapi = scapi;
	}

	public BigDecimal getScontamt() {
		return scontamt;
	}

	public void setScontamt(BigDecimal scontamt) {
		this.scontamt = scontamt;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public BigDecimal getSfine() {
		return sfine;
	}

	public void setSfine(BigDecimal sfine) {
		this.sfine = sfine;
	}

	public BigDecimal getSinsuamt() {
		return sinsuamt;
	}

	public void setSinsuamt(BigDecimal sinsuamt) {
		this.sinsuamt = sinsuamt;
	}

	public BigDecimal getSinte() {
		return sinte;
	}

	public void setSinte(BigDecimal sinte) {
		this.sinte = sinte;
	}

	public String getSterm() {
		return sterm;
	}

	public void setSterm(String sterm) {
		this.sterm = sterm;
	}

	public String getsPayLiqDam() {
		return sPayLiqDam;
	}

	public void setsPayLiqDam(String sPayLiqDam) {
		this.sPayLiqDam = sPayLiqDam;
	}

	public String getrPayLiqDam() {
		return rPayLiqDam;
	}

	public void setrPayLiqDam(String rPayLiqDam) {
		this.rPayLiqDam = rPayLiqDam;
	}

	public String getsRecPayLiqDam() {
		return sRecPayLiqDam;
	}

	public void setsRecPayLiqDam(String sRecPayLiqDam) {
		this.sRecPayLiqDam = sRecPayLiqDam;
	}

	public String getrRecPayLiqDam() {
		return rRecPayLiqDam;
	}

	public void setrRecPayLiqDam(String rRecPayLiqDam) {
		this.rRecPayLiqDam = rRecPayLiqDam;
	}

	@Override
	public String toString() {
		return "LoanRepaymentPlanDetail [guarantyid=" + guarantyid + ", loanacno=" + loanacno + ", overflag=" + overflag
				+ ", payoffdate=" + payoffdate + ", payoffflag=" + payoffflag + ", rcapi=" + rcapi + ", rcontamt="
				+ rcontamt + ", rfine=" + rfine + ", rinsuamt=" + rinsuamt + ", rinte=" + rinte + ", scapi=" + scapi
				+ ", scontamt=" + scontamt + ", sdate=" + sdate + ", sfine=" + sfine + ", sinsuamt=" + sinsuamt
				+ ", sinte=" + sinte + ", sterm=" + sterm + ", sPayLiqDam=" + sPayLiqDam + ", rPayLiqDam=" + rPayLiqDam
				+ ", sRecPayLiqDam=" + sRecPayLiqDam + ", rRecPayLiqDam=" + rRecPayLiqDam + "]";
	}

}
