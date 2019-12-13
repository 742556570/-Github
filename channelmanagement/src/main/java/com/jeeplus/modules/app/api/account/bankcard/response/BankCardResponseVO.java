package com.jeeplus.modules.app.api.account.bankcard.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
@XStreamAlias("OUT")
public class BankCardResponseVO {

	@XStreamAlias("HEADER")
	private DataHeader dataHeader;
	@XStreamAlias("BODY")
	private BankCardResponseBody bankCardResponseBody;
	public DataHeader getDataHeader() {
		return dataHeader;
	}
	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}
	public BankCardResponseBody getBankCardResponseBody() {
		return bankCardResponseBody;
	}
	public void setBankCardResponseBody(BankCardResponseBody bankCardResponseBody) {
		this.bankCardResponseBody = bankCardResponseBody;
	}
	@Override
	public String toString() {
		return "BankCardResponseVO [dataHeader=" + dataHeader
				+ ", bankCardResponseBody=" + bankCardResponseBody + "]";
	}
	
	
}
