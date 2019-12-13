package com.jeeplus.modules.app.api.account.CreditLoanApply.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小额信用贷款申请请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class BODY {

	private RECORD RECORD ;

	public RECORD getRECORD() {
		return RECORD;
	}

	public void setRECORD(RECORD rECORD) {
		RECORD = rECORD;
	}

	
}
