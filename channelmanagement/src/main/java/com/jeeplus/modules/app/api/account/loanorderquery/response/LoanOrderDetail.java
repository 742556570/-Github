package com.jeeplus.modules.app.api.account.loanorderquery.response;

import java.math.BigDecimal;

/**
 * 小额信用贷款申请请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class LoanOrderDetail {

	private String loanacno;
	private String guarantyid;
	private BigDecimal loanamt;
	private String loanterm;
	private String retukind;
	private String begindate;
	private String enddate;
	private String sterm;
	private String sdate;
	private BigDecimal bal;
	private String accflag;
	private String payChannel;

	public String getLoanacno() {
		return loanacno;
	}

	public void setLoanacno(String loanacno) {
		this.loanacno = loanacno;
	}

	public String getGuarantyid() {
		return guarantyid;
	}

	public void setGuarantyid(String guarantyid) {
		this.guarantyid = guarantyid;
	}

	public BigDecimal getLoanamt() {
		return loanamt;
	}

	public void setLoanamt(BigDecimal loanamt) {
		this.loanamt = loanamt;
	}

	public String getLoanterm() {
		return loanterm;
	}

	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}

	public String getRetukind() {
		return retukind;
	}

	public void setRetukind(String retukind) {
		this.retukind = retukind;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getSterm() {
		return sterm;
	}

	public void setSterm(String sterm) {
		this.sterm = sterm;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getAccflag() {
		return accflag;
	}

	public void setAccflag(String accflag) {
		this.accflag = accflag;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@Override
	public String toString() {
		return "LoanOrderDetail [loanacno=" + loanacno + ", guarantyid=" + guarantyid + ", loanamt=" + loanamt
				+ ", loanterm=" + loanterm + ", retukind=" + retukind + ", begindate=" + begindate + ", enddate="
				+ enddate + ", sterm=" + sterm + ", sdate=" + sdate + ", bal=" + bal + ", accflag=" + accflag
				+ ", payChannel=" + payChannel + "]";
	}

}
