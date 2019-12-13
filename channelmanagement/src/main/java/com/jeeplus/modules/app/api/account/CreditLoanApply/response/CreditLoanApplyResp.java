package com.jeeplus.modules.app.api.account.CreditLoanApply.response;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("OUT")
public class CreditLoanApplyResp {

	@XStreamAlias("HEADER")
	private DataHeader  dataHeader;
	
	@XStreamAlias("BODY")
	private CreditLoanApplyRespDataBody dataBody;

	public DataHeader getDataHeader() {
		return dataHeader;
	}

	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}

	public CreditLoanApplyRespDataBody getDataBody() {
		return dataBody;
	}

	public void setDataBody(CreditLoanApplyRespDataBody dataBody) {
		this.dataBody = dataBody;
	}
	
	
}
