package com.jeeplus.modules.app.api.account.LoanPrePayment.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LoanPrepaymentRespDataBody {

	@XStreamAlias("RECORD")
	private LoanPrepaymentRespDataRecord  dataRecord;

	public LoanPrepaymentRespDataRecord getDataRecord() {
		return dataRecord;
	}

	public void setDataRecord(LoanPrepaymentRespDataRecord dataRecord) {
		this.dataRecord = dataRecord;
	}
	
	
}
