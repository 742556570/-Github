package com.jeeplus.modules.app.api.account.paychannel.request;

import java.io.Serializable;

/**
 * 同步产品信息请求参数vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqPayChannelBankCardVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String acNo;// 客户银行卡号
	private String acName;// 账户名称
	private String bankCode;// 银行编号
	private String idType;// 证件类型
	private String idNo;// 证件号
	private String acType;// 账户类型
	private String phoneNo;// 银行预留手机号;
	private String cardChn;// 支付渠道

	public String getCardChn() {
		return cardChn;
	}

	public void setCardChn(String cardChn) {
		this.cardChn = cardChn;
	}

	public String getAcNo() {
		return acNo;
	}

	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReqPayChannelBankCardVO [acNo=" + acNo + ", acName=" + acName + ", bankCode=" + bankCode + ", idType="
				+ idType + ", idNo=" + idNo + ", acType=" + acType + ", phoneNo=" + phoneNo + "]";
	}

}
