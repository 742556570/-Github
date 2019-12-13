package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("OUT")
public class LoanRepaymentPlanBankResp {

	@XStreamAlias("HEADER")
	private DataHeader dataHeader;
	
	@XStreamAlias("BODY")
	private LoanRepaymentPlanBankRespDataBody  dataBody;

	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}

	public LoanRepaymentPlanBankRespDataBody getDataBody() {
		return dataBody;
	}

	public void setDataBody(LoanRepaymentPlanBankRespDataBody dataBody) {
		this.dataBody = dataBody;
	}
	
	
}
