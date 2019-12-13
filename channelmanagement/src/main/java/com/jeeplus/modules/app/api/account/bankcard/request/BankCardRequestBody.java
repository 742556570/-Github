package com.jeeplus.modules.app.api.account.bankcard.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
public class BankCardRequestBody {

	@XStreamAlias("RECORD")
	private BankCardRequestRecord bankCardRequestRecord;

	public BankCardRequestRecord getBankCardRequestRecord() {
		return bankCardRequestRecord;
	}

	public void setBankCardRequestRecord(BankCardRequestRecord bankCardRequestRecord) {
		this.bankCardRequestRecord = bankCardRequestRecord;
	}
	
	
}
