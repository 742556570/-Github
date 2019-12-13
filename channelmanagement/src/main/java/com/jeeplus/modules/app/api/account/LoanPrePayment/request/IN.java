package com.jeeplus.modules.app.api.account.LoanPrePayment.request;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * 提前还款请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement
public class IN  {
	//header
	private HEADER HEADER;
    private BODY BODY;
	public HEADER getHEADER() {
		return HEADER;
	}
	public void setHEADER(HEADER hEADER) {
		HEADER = hEADER;
	}
	public BODY getBODY() {
		return BODY;
	}
	public void setBODY(BODY bODY) {
		BODY = bODY;
	}
 
}
