package com.jeeplus.modules.app.api.account.CreditLoanApplyQuery.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小额信用贷款申请查询请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class RECORD  {

    //body
	private String GUARANTYID;// 保单号

	public String getGUARANTYID() {
		return GUARANTYID;
	}

	public void setGUARANTYID(String gUARANTYID) {
		GUARANTYID = gUARANTYID;
	}

	@Override
	public String toString() {
		return "RECORD [GUARANTYID=" + GUARANTYID + "]";
	}
}
