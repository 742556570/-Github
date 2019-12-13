package com.jeeplus.modules.app.api.account.loanorderquery.response;


import javax.xml.bind.annotation.XmlRootElement;

/**
 * 小额信贷订单申请请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
@XmlRootElement(name="OUT")
public class OUT  {

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
	@Override
	public String toString() {
		return "IN [HEADER=" + HEADER + ", BODY=" + BODY + "]";
	}
 
}
