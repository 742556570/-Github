package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CreditLoanApplyRespDataBody {

	@XStreamAlias("RECORD")
	private CreditLoanApplyRespDataRecord  dataRecord;

	public CreditLoanApplyRespDataRecord getDataRecord() {
		return dataRecord;
	}

	public void setDataRecord(CreditLoanApplyRespDataRecord dataRecord) {
		this.dataRecord = dataRecord;
	}
	
	
}
