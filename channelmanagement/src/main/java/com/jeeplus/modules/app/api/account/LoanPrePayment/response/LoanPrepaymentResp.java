package com.jeeplus.modules.app.api.account.LoanPrePayment.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("OUT")
public class LoanPrepaymentResp {
	
	@XStreamAlias("HEADER") 
	private DataHeader dataHeader;
	@XStreamAlias("BODY") 
	private LoanPrepaymentRespDataBody dataBody;
	public DataHeader getDataHeader() {
		return dataHeader;
	}
	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}
	public LoanPrepaymentRespDataBody getDataBody() {
		return dataBody;
	}
	public void setDataBody(LoanPrepaymentRespDataBody dataBody) {
		this.dataBody = dataBody;
	}
	
	
}
