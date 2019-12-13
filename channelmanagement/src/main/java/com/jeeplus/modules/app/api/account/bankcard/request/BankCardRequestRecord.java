package com.jeeplus.modules.app.api.account.bankcard.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
public class BankCardRequestRecord {

	// 保单号
	@XStreamAlias("GUARANTYID")
	private String guarantyId;
	
	// 借据号
	@XStreamAlias("LNACCT")
	private String loanId;
	
	// 新卡号
	@XStreamAlias("PAYMENTACCTNO")
	private String bankCardNo;
	
	// 银行卡代码
	@XStreamAlias("BANKCODE")
	private String bankCode;
	
	// 开户行省代码
	@XStreamAlias("BANKPROVINCECODE")
	private String bankProvinceCode;
	
	// 开户行省名称
	@XStreamAlias("BANKPROVINCENAME")
	private String bankProvinceName;
	
	// 开户行市代码
	@XStreamAlias("BANKCITYCODE")
	private String bankCityCode;
	// 开户行市名称
	@XStreamAlias("BANKCITYNAME")
	private String bankCityName;
	
	// 账户名称
	@XStreamAlias("BANKACCTNAME")
	private String bankAcctName;
	// 预留手机号
	@XStreamAlias("MOBILE")
	private String mobile;
	
	// 银行预留手机号
	@XStreamAlias("BANKMOBILE")
	private String bankMobile;
	
	//证件类型
	@XStreamAlias("CERTTYPE")
	private String certificateType;
	
	// 证件号码
	@XStreamAlias("CERTID")
	private String certificateNo;
	
	// 放款业务收款方银行代码
	@XStreamAlias("RECEIVEBANKCODE")
	private String receiveBankCode;
	
	// 放款业务收款方户名
	@XStreamAlias("RECEIVEACCTNAME")
	private String receiveBankName;
	
	// 放款业务收款方账号
	@XStreamAlias("RECEIVEACCTNO")
	private String receiveAcctNo;
	
	// 开户人名称
	@XStreamAlias("BANKOWNER")
	private String bankOwerName;
	
	// 银行名称
	@XStreamAlias("BANKNAME")
	private String bankName;

	public String getGuarantyId() {
		return guarantyId;
	}

	public void setGuarantyId(String guarantyId) {
		this.guarantyId = guarantyId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankProvinceCode() {
		return bankProvinceCode;
	}

	public void setBankProvinceCode(String bankProvinceCode) {
		this.bankProvinceCode = bankProvinceCode;
	}

	public String getBankProvinceName() {
		return bankProvinceName;
	}

	public void setBankProvinceName(String bankProvinceName) {
		this.bankProvinceName = bankProvinceName;
	}

	public String getBankCityCode() {
		return bankCityCode;
	}

	public void setBankCityCode(String bankCityCode) {
		this.bankCityCode = bankCityCode;
	}

	public String getBankCityName() {
		return bankCityName;
	}

	public void setBankCityName(String bankCityName) {
		this.bankCityName = bankCityName;
	}

	public String getBankAcctName() {
		return bankAcctName;
	}

	public void setBankAcctName(String bankAcctName) {
		this.bankAcctName = bankAcctName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getReceiveBankCode() {
		return receiveBankCode;
	}

	public void setReceiveBankCode(String receiveBankCode) {
		this.receiveBankCode = receiveBankCode;
	}

	public String getReceiveBankName() {
		return receiveBankName;
	}

	public void setReceiveBankName(String receiveBankName) {
		this.receiveBankName = receiveBankName;
	}

	public String getReceiveAcctNo() {
		return receiveAcctNo;
	}

	public void setReceiveAcctNo(String receiveAcctNo) {
		this.receiveAcctNo = receiveAcctNo;
	}

	public String getBankOwerName() {
		return bankOwerName;
	}

	public void setBankOwerName(String bankOwerName) {
		this.bankOwerName = bankOwerName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
}
