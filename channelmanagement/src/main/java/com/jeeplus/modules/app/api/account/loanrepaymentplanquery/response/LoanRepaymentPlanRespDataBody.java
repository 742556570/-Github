package com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LoanRepaymentPlanRespDataBody {
	
	@XStreamAlias("RECORD")
	private LeanRepaymentPlanRespDataRecord  dataRecord;

	public LeanRepaymentPlanRespDataRecord getDataRecord() {
		return dataRecord;
	}

	public void setDataRecord(LeanRepaymentPlanRespDataRecord dataRecord) {
		this.dataRecord = dataRecord;
	}
	
	
}
