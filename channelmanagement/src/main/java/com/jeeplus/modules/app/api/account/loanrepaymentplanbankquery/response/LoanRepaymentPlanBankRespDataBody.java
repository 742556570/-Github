package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class LoanRepaymentPlanBankRespDataBody {

	@XStreamAlias("RECORD")
	private LeanRepaymentPlanBankRespDataRecord dataRecord;

	public LeanRepaymentPlanBankRespDataRecord getDataRecord() {
		return dataRecord;
	}

	public void setDataRecord(LeanRepaymentPlanBankRespDataRecord dataRecord) {
		this.dataRecord = dataRecord;
	}

}
