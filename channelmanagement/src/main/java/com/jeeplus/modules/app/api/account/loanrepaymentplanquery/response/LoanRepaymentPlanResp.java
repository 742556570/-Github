package com.jeeplus.modules.app.api.account.loanrepaymentplanquery.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("OUT")
public class LoanRepaymentPlanResp {

	@XStreamAlias("HEADER")
	private DataHeader dataHeader;
	
	@XStreamAlias("BODY")
	private LoanRepaymentPlanRespDataBody  dataBody;

	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}

	public LoanRepaymentPlanRespDataBody getDataBody() {
		return dataBody;
	}

	public void setDataBody(LoanRepaymentPlanRespDataBody dataBody) {
		this.dataBody = dataBody;
	}
	
	
}
