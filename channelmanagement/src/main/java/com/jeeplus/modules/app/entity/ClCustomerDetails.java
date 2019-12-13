package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 客户信息
 * 
 * @author 阳光保险
 *
 */

public class ClCustomerDetails extends DataEntity<ClCustomerDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CUST_ID; // 客户编号
	private String POLICY_NO;// 保单号
	private String ID_NO;// 证件号码
	private String ID_TYPE;// 证件类型
	private String TITLE; // 称谓
	private String NAME; // 姓名
	private String GENDER;// 性别
	private String BIRTHDAY;// 生日(YYYYMMDD)
	private String OCCUPATION;// 职业
	private String BANKMEMBER_NO;// 本行员工号
	private String MARITAL_STATUS; // 婚姻状况
	private String QUALIFICATION; // 教育状况
	private String HOUSE_OWNERSHIP; // 房屋持有类型
	private String HOUSE_TYPE; // 住宅类型
	private String LIQUID_ASSET; // 个人资产类型
	private String MOBILE_NO; // 移动电话
	private String EMAIL; // 电子邮箱
	private String EMP_STATUS; // 就业状态
	private String NBR_OF_DEPENDENTS; // 抚养人数
	private String SOCIAL_INS_AMT; // 社保缴存金额
	private String CORP_NAME; // 公司名称
	private String FAMILY_KNOW; // 家人是否知晓此项贷款
	private String HASCAR; // 是否有车
	private String HOMEADDRESS; // 居住地址
	private String BRANCH_ID; // 门店
	private String BRANCH_CODE; // 门店代码

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

	public String getID_NO() {
		return ID_NO;
	}

	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}

	public String getID_TYPE() {
		return ID_TYPE;
	}

	public void setID_TYPE(String iD_TYPE) {
		ID_TYPE = iD_TYPE;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getGENDER() {
		return GENDER;
	}

	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getOCCUPATION() {
		return OCCUPATION;
	}

	public void setOCCUPATION(String oCCUPATION) {
		OCCUPATION = oCCUPATION;
	}

	public String getMARITAL_STATUS() {
		return MARITAL_STATUS;
	}

	public void setMARITAL_STATUS(String mARITAL_STATUS) {
		MARITAL_STATUS = mARITAL_STATUS;
	}

	public String getQUALIFICATION() {
		return QUALIFICATION;
	}

	public void setQUALIFICATION(String qUALIFICATION) {
		QUALIFICATION = qUALIFICATION;
	}

	public String getHOUSE_OWNERSHIP() {
		return HOUSE_OWNERSHIP;
	}

	public void setHOUSE_OWNERSHIP(String hOUSE_OWNERSHIP) {
		HOUSE_OWNERSHIP = hOUSE_OWNERSHIP;
	}

	public String getHOUSE_TYPE() {
		return HOUSE_TYPE;
	}

	public void setHOUSE_TYPE(String hOUSE_TYPE) {
		HOUSE_TYPE = hOUSE_TYPE;
	}

	public String getLIQUID_ASSET() {
		return LIQUID_ASSET;
	}

	public void setLIQUID_ASSET(String lIQUID_ASSET) {
		LIQUID_ASSET = lIQUID_ASSET;
	}

	public String getMOBILE_NO() {
		return MOBILE_NO;
	}

	public void setMOBILE_NO(String mOBILE_NO) {
		MOBILE_NO = mOBILE_NO;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getEMP_STATUS() {
		return EMP_STATUS;
	}

	public void setEMP_STATUS(String eMP_STATUS) {
		EMP_STATUS = eMP_STATUS;
	}

	public String getNBR_OF_DEPENDENTS() {
		return NBR_OF_DEPENDENTS;
	}

	public void setNBR_OF_DEPENDENTS(String nBR_OF_DEPENDENTS) {
		NBR_OF_DEPENDENTS = nBR_OF_DEPENDENTS;
	}

	public String getSOCIAL_INS_AMT() {
		return SOCIAL_INS_AMT;
	}

	public void setSOCIAL_INS_AMT(String sOCIAL_INS_AMT) {
		SOCIAL_INS_AMT = sOCIAL_INS_AMT;
	}

	public String getCORP_NAME() {
		return CORP_NAME;
	}

	public void setCORP_NAME(String cORP_NAME) {
		CORP_NAME = cORP_NAME;
	}

	public String getFAMILY_KNOW() {
		return FAMILY_KNOW;
	}

	public void setFAMILY_KNOW(String fAMILY_KNOW) {
		FAMILY_KNOW = fAMILY_KNOW;
	}

	public String getHASCAR() {
		return HASCAR;
	}

	public void setHASCAR(String hASCAR) {
		HASCAR = hASCAR;
	}

	public String getHOMEADDRESS() {
		return HOMEADDRESS;
	}

	public void setHOMEADDRESS(String hOMEADDRESS) {
		HOMEADDRESS = hOMEADDRESS;
	}

	public String getBRANCH_ID() {
		return BRANCH_ID;
	}

	public void setBRANCH_ID(String bRANCH_ID) {
		BRANCH_ID = bRANCH_ID;
	}

	public String getBRANCH_CODE() {
		return BRANCH_CODE;
	}

	public void setBRANCH_CODE(String bRANCH_CODE) {
		BRANCH_CODE = bRANCH_CODE;
	}

	public String getBANKMEMBER_NO() {
		return BANKMEMBER_NO;
	}

	public void setBANKMEMBER_NO(String bANKMEMBER_NO) {
		BANKMEMBER_NO = bANKMEMBER_NO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ClCustomerDetails [CUST_ID=" + CUST_ID + ", POLICY_NO=" + POLICY_NO + ", ID_NO=" + ID_NO + ", ID_TYPE="
				+ ID_TYPE + ", TITLE=" + TITLE + ", NAME=" + NAME + ", GENDER=" + GENDER + ", BIRTHDAY=" + BIRTHDAY
				+ ", OCCUPATION=" + OCCUPATION + ", BANKMEMBER_NO=" + BANKMEMBER_NO + ", MARITAL_STATUS="
				+ MARITAL_STATUS + ", QUALIFICATION=" + QUALIFICATION + ", HOUSE_OWNERSHIP=" + HOUSE_OWNERSHIP
				+ ", HOUSE_TYPE=" + HOUSE_TYPE + ", LIQUID_ASSET=" + LIQUID_ASSET + ", MOBILE_NO=" + MOBILE_NO
				+ ", EMAIL=" + EMAIL + ", EMP_STATUS=" + EMP_STATUS + ", NBR_OF_DEPENDENTS=" + NBR_OF_DEPENDENTS
				+ ", SOCIAL_INS_AMT=" + SOCIAL_INS_AMT + ", CORP_NAME=" + CORP_NAME + ", FAMILY_KNOW=" + FAMILY_KNOW
				+ ", HASCAR=" + HASCAR + ", HOMEADDRESS=" + HOMEADDRESS + ", BRANCH_ID=" + BRANCH_ID + ", BRANCH_CODE="
				+ BRANCH_CODE + "]";
	}

}
