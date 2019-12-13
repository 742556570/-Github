package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.jeeplus.modules.app.api.account.header.DataHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement
@XStreamAlias("OUT")
public class CreditLoanApplyQueryResp {

	@XStreamAlias("HEADER") 
	private  DataHeader  dataHeader;
	
	@XStreamAlias("BODY")
	private CreditLoanApplyRespDataBody dataBody;
//	private String GUARANTYID;
//	private String HANDLECODE;
//	private String HANDLEINFO;
//	private String BUSISTATUS;
//	private String BUSIDESC;
//	private String IN;
//	private String HEADER;
//	private String PATCH;
//	private String TRANCODE;
//	private String TRANTIME;
//	private String DATASOURCE;
//	private String BODY;
//	private String RECORD;

	
	
	public DataHeader getDataHeader() {
		return dataHeader;
	}

	 

	public CreditLoanApplyRespDataBody getDataBody() {
		return dataBody;
	}



	public void setDataBody(CreditLoanApplyRespDataBody dataBody) {
		this.dataBody = dataBody;
	}



	public void setDataHeader(DataHeader dataHeader) {
		this.dataHeader = dataHeader;
	}
	
	 
}
