package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 地址信息
 * 
 * @author 阳光保险
 *
 */
public class ClPolicyDetails extends DataEntity<ClPolicyDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ACCT_NO; // 账户编号
	private String CUST_ID;// 客户编号
	private String POLICY_NO;// 保单号
	private String PRO_NOTE_NO; // 借据号
	private String APPLY_CODE;// 申请号
	private String CITY_CODE;// 城市码
	private String BANK;// 合作银行
	private String BRANCHES_CODE; // 合作银行分支行代码
	private String BRANCHES_NAME; // 合作银行分支行名称
	private String PRODUCT_CODE; // 贷款种类
	private String LOAN_PURPOSE; // 贷款用途
	private String PRODUCT_TYPE; // 债项类型
	private String LOAN_RATE; // 贷款利率
	private String PREMIUN_RATE; // 保费率
	private String RISK_CODE; // 险种代码
	private String COM_CODE; // 归属业务机构
	private String BUSINESS_TYPE; // 业务类型
	private String SIGN_CUST_NAME; // 签约客服姓名
	private String PhPremiumRate; // 普惠保费费率
	private String POUNDAGE_RATE; // 首期服务费率
	private String MANAGEMENT_RATE; // 期交服务费率

	public String getACCT_NO() {
		return ACCT_NO;
	}

	public void setACCT_NO(String aCCT_NO) {
		ACCT_NO = aCCT_NO;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}

	public String getPOLICY_NO() {
		return POLICY_NO;
	}

	public void setPOLICY_NO(String pOLICY_NO) {
		POLICY_NO = pOLICY_NO;
	}

	public String getPRO_NOTE_NO() {
		return PRO_NOTE_NO;
	}

	public void setPRO_NOTE_NO(String pRO_NOTE_NO) {
		PRO_NOTE_NO = pRO_NOTE_NO;
	}

	public String getAPPLY_CODE() {
		return APPLY_CODE;
	}

	public void setAPPLY_CODE(String aPPLY_CODE) {
		APPLY_CODE = aPPLY_CODE;
	}

	public String getCITY_CODE() {
		return CITY_CODE;
	}

	public void setCITY_CODE(String cITY_CODE) {
		CITY_CODE = cITY_CODE;
	}

	public String getBANK() {
		return BANK;
	}

	public void setBANK(String bANK) {
		BANK = bANK;
	}

	public String getBRANCHES_CODE() {
		return BRANCHES_CODE;
	}

	public void setBRANCHES_CODE(String bRANCHES_CODE) {
		BRANCHES_CODE = bRANCHES_CODE;
	}

	public String getBRANCHES_NAME() {
		return BRANCHES_NAME;
	}

	public void setBRANCHES_NAME(String bRANCHES_NAME) {
		BRANCHES_NAME = bRANCHES_NAME;
	}

	public String getPRODUCT_CODE() {
		return PRODUCT_CODE;
	}

	public void setPRODUCT_CODE(String pRODUCT_CODE) {
		PRODUCT_CODE = pRODUCT_CODE;
	}

	public String getLOAN_PURPOSE() {
		return LOAN_PURPOSE;
	}

	public void setLOAN_PURPOSE(String lOAN_PURPOSE) {
		LOAN_PURPOSE = lOAN_PURPOSE;
	}

	public String getPRODUCT_TYPE() {
		return PRODUCT_TYPE;
	}

	public void setPRODUCT_TYPE(String pRODUCT_TYPE) {
		PRODUCT_TYPE = pRODUCT_TYPE;
	}

	public String getLOAN_RATE() {
		return LOAN_RATE;
	}

	public void setLOAN_RATE(String lOAN_RATE) {
		LOAN_RATE = lOAN_RATE;
	}

	public String getPREMIUN_RATE() {
		return PREMIUN_RATE;
	}

	public void setPREMIUN_RATE(String pREMIUN_RATE) {
		PREMIUN_RATE = pREMIUN_RATE;
	}

	public String getRISK_CODE() {
		return RISK_CODE;
	}

	public void setRISK_CODE(String rISK_CODE) {
		RISK_CODE = rISK_CODE;
	}

	public String getCOM_CODE() {
		return COM_CODE;
	}

	public void setCOM_CODE(String cOM_CODE) {
		COM_CODE = cOM_CODE;
	}

	public String getBUSINESS_TYPE() {
		return BUSINESS_TYPE;
	}

	public void setBUSINESS_TYPE(String bUSINESS_TYPE) {
		BUSINESS_TYPE = bUSINESS_TYPE;
	}

	public String getSIGN_CUST_NAME() {
		return SIGN_CUST_NAME;
	}

	public void setSIGN_CUST_NAME(String sIGN_CUST_NAME) {
		SIGN_CUST_NAME = sIGN_CUST_NAME;
	}

	public String getPhPremiumRate() {
		return PhPremiumRate;
	}

	public void setPhPremiumRate(String phPremiumRate) {
		PhPremiumRate = phPremiumRate;
	}

	public String getPOUNDAGE_RATE() {
		return POUNDAGE_RATE;
	}

	public void setPOUNDAGE_RATE(String pOUNDAGE_RATE) {
		POUNDAGE_RATE = pOUNDAGE_RATE;
	}

	public String getMANAGEMENT_RATE() {
		return MANAGEMENT_RATE;
	}

	public void setMANAGEMENT_RATE(String mANAGEMENT_RATE) {
		MANAGEMENT_RATE = mANAGEMENT_RATE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ClPolicyMsg [ACCT_NO=" + ACCT_NO + ", CUST_ID=" + CUST_ID + ", POLICY_NO=" + POLICY_NO
				+ ", PRO_NOTE_NO=" + PRO_NOTE_NO + ", APPLY_CODE=" + APPLY_CODE + ", CITY_CODE=" + CITY_CODE + ", BANK="
				+ BANK + ", BRANCHES_CODE=" + BRANCHES_CODE + ", BRANCHES_NAME=" + BRANCHES_NAME + ", PRODUCT_CODE="
				+ PRODUCT_CODE + ", LOAN_PURPOSE=" + LOAN_PURPOSE + ", PRODUCT_TYPE=" + PRODUCT_TYPE + ", LOAN_RATE="
				+ LOAN_RATE + ", PREMIUN_RATE=" + PREMIUN_RATE + ", RISK_CODE=" + RISK_CODE + ", COM_CODE=" + COM_CODE
				+ ", BUSINESS_TYPE=" + BUSINESS_TYPE + ", SIGN_CUST_NAME=" + SIGN_CUST_NAME + ", PhPremiumRate="
				+ PhPremiumRate + ", POUNDAGE_RATE=" + POUNDAGE_RATE + ", MANAGEMENT_RATE=" + MANAGEMENT_RATE + "]";
	}

}
