package com.jeeplus.modules.app.api.account.bankcard.request;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
@XStreamAlias("IN")
public class BankCardRequestVO {
	
	@XStreamAlias("HEADER")
	private DataHeader dataHeader;
	@XStreamAlias("BODY")
	private BankCardRequestBody bankdCardRequestBody;
	public DataHeader getDataHeader() {
		return dataHeader;
	}
	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}
	public BankCardRequestBody getBankdCardRequestBody() {
		return bankdCardRequestBody;
	}
	public void setBankdCardRequestBody(BankCardRequestBody bankdCardRequestBody) {
		this.bankdCardRequestBody = bankdCardRequestBody;
	}
	
	
}
