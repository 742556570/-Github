package com.jeeplus.modules.app.api.account.LoanPrePayment.request;

/**
 * 贷款提前还款请求参数vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class RECORD  {


	private String GUARANTYID;// 保单号
	private String LNACCT;// 还款日期
	private String TYPE;// 类型 1.提前还款试算2.提前还款预约3.取消提前还款
	private String CALDATE;// 还款日期
	private String PAYMENTACCTNO;// 借记卡号

	public String getGUARANTYID() {
		return GUARANTYID;
	}

	public void setGUARANTYID(String gUARANTYID) {
		GUARANTYID = gUARANTYID;
	}

	public String getLNACCT() {
		return LNACCT;
	}

	public void setLNACCT(String lNACCT) {
		LNACCT = lNACCT;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getCALDATE() {
		return CALDATE;
	}

	public void setCALDATE(String cALDATE) {
		CALDATE = cALDATE;
	}

	public String getPAYMENTACCTNO() {
		return PAYMENTACCTNO;
	}

	public void setPAYMENTACCTNO(String pAYMENTACCTNO) {
		PAYMENTACCTNO = pAYMENTACCTNO;
	}


}
