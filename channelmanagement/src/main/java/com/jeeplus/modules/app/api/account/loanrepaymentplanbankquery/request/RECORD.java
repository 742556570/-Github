package com.jeeplus.modules.app.api.account.loanrepaymentplanbankquery.request;

/**
 * 贷款还款计划查询请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class RECORD {

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
