package com.jeeplus.modules.app.api.account.bankcard.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;



/**
 **@author 
 **@date 2018年7月2日
 **/
public class BankCardResponseBody {

	@XStreamAlias("RECORD") 
	private BankCardResponseRecord bankCardResponseRecord;

	public BankCardResponseRecord getBankCardResponseRecord() {
		return bankCardResponseRecord;
	}

	public void setBankCardResponseRecord(
			BankCardResponseRecord bankCardResponseRecord) {
		this.bankCardResponseRecord = bankCardResponseRecord;
	}

	@Override
	public String toString() {
		return "BankCardResponseBody [bankCardResponseRecord="
				+ bankCardResponseRecord + "]";
	}
	
	
}
