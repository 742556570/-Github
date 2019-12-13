package com.jeeplus.modules.app.api.account.LoanPrePayment.response;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LoanPrepaymentRespDataRecord {
	@XStreamAlias("BUSINESSID")
	private String buinessId;
	@XStreamAlias("HANDLECODE")
	private String handleCode;
	@XStreamAlias("HANDLEINFO")
	private String handleInfo;
	@XStreamAlias("AMOUNT")
	private String amount;

	@XStreamAlias("PREMIUM")
	private String premium;
	@XStreamAlias("POUNDAGE")
	private String poundage;
	@XStreamAlias("INTEREST")
	private String interest;
	@XStreamAlias("PRINCIPAL")
	private String principal;
	@XStreamAlias("SOVERAMT")
	private String soveramt;
	@XStreamAlias("SNTERMAMT")
	private String sntermamt;
	@XStreamAlias("SCAPIINTE")
	private String scapiinte;
	@XStreamAlias("SINSUAMT")
	private String sinsuamt;
	@XStreamAlias("SBAL")
	private String sbal;
	@XStreamAlias("SRETUINSUAMT")
	private String sretuinsuamt;
	@XStreamAlias("REFUNDAMT")
	private String pefundamt;
	@XStreamAlias("SOVERCAPIINTE")
	private String soverCapiinte;

	@XStreamAlias("SOVERINSUAMT")
	private String soverinsuAmt;
	@XStreamAlias("SCONTAMT")
	private String scontamt;
	@XStreamAlias("SOVERCONTAMT")
	private String sovercontAmt;
	/*********************** 以下字段报文中有，接口文档中无 ***********************************/
	@XStreamAlias("COMMINF")
	private String comminf;
	@XStreamAlias("SCAPI")
	private String scapi;
	@XStreamAlias("SINTE")
	private String sinte;

	@XStreamAlias("SOVERCAPI")
	private String sovercapi;

	@XStreamAlias("SOVERINTE")
	private String soverinte;
	@XStreamAlias("SOVERFINE")
	private String soverfine;

	public String getScapi() {
		return scapi;
	}

	public void setScapi(String scapi) {
		this.scapi = scapi;
	}

	public String getBuinessId() {
		return buinessId;
	}

	public void setBuinessId(String buinessId) {
		this.buinessId = buinessId;
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

	public String getPremium() {
		return premium;
	}

	public void setPremium(String premium) {
		this.premium = premium;
	}

	public String getPoundage() {
		return poundage;
	}

	public void setPoundage(String poundage) {
		this.poundage = poundage;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getSoveramt() {
		return soveramt;
	}

	public void setSoveramt(String soveramt) {
		this.soveramt = soveramt;
	}

	public String getSntermamt() {
		return sntermamt;
	}

	public void setSntermamt(String sntermamt) {
		this.sntermamt = sntermamt;
	}

	public String getScapiinte() {
		return scapiinte;
	}

	public void setScapiinte(String scapiinte) {
		this.scapiinte = scapiinte;
	}

	public String getSinsuamt() {
		return sinsuamt;
	}

	public void setSinsuamt(String sinsuamt) {
		this.sinsuamt = sinsuamt;
	}

	public String getSbal() {
		return sbal;
	}

	public void setSbal(String sbal) {
		this.sbal = sbal;
	}

	public String getSretuinsuamt() {
		return sretuinsuamt;
	}

	public void setSretuinsuamt(String sretuinsuamt) {
		this.sretuinsuamt = sretuinsuamt;
	}

	public String getPefundamt() {
		return pefundamt;
	}

	public void setPefundamt(String pefundamt) {
		this.pefundamt = pefundamt;
	}

	public String getSoverCapiinte() {
		return soverCapiinte;
	}

	public void setSoverCapiinte(String soverCapiinte) {
		this.soverCapiinte = soverCapiinte;
	}

	public String getSoverinsuAmt() {
		return soverinsuAmt;
	}

	public void setSoverinsuAmt(String soverinsuAmt) {
		this.soverinsuAmt = soverinsuAmt;
	}

	public String getComminf() {
		return comminf;
	}

	public void setComminf(String comminf) {
		this.comminf = comminf;
	}

	public String getScontamt() {
		return scontamt;
	}

	public String getSovercontAmt() {
		return sovercontAmt;
	}

	public void setSovercontAmt(String sovercontAmt) {
		this.sovercontAmt = sovercontAmt;
	}

	public void setScontamt(String scontamt) {
		this.scontamt = scontamt;
	}



	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "LoanPrepaymentRespDataRecord [buinessId=" + buinessId + ", handleCode=" + handleCode + ", handleInfo="
				+ handleInfo + ", amount=" + amount + ", premium=" + premium + ", poundage=" + poundage + ", interest="
				+ interest + ", principal=" + principal + ", soveramt=" + soveramt + ", sntermamt=" + sntermamt
				+ ", scapiinte=" + scapiinte + ", sinsuamt=" + sinsuamt + ", sbal=" + sbal + ", sretuinsuamt="
				+ sretuinsuamt + ", pefundamt=" + pefundamt + ", soverCapiinte=" + soverCapiinte + ", soverinsuAmt="
				+ soverinsuAmt + ", scontamt=" + scontamt + ", sovercontAmt=" + sovercontAmt + "]";
	}

	public String getSinte() {
		return sinte;
	}

	public void setSinte(String sinte) {
		this.sinte = sinte;
	}

	public String getSovercapi() {
		return sovercapi;
	}

	public void setSovercapi(String sovercapi) {
		this.sovercapi = sovercapi;
	}

	public String getSoverinte() {
		return soverinte;
	}

	public void setSoverinte(String soverinte) {
		this.soverinte = soverinte;
	}

	public String getSoverfine() {
		return soverfine;
	}

	public void setSoverfine(String soverfine) {
		this.soverfine = soverfine;
	}

}
