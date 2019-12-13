package com.jeeplus.modules.app.api.account.LoanPrePayment.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 提前还款请求参数vo
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
