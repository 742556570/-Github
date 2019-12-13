package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 电话信息
 * @author 阳光保险
 *
 */
public class ClMobileDetails extends DataEntity<ClMobileDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CONTACT_ID; // 联系人ID
	private String CUST_ID;// 客户编号
	private String RELATIONSHIP;// 与持卡人关系
	private String NAME; // 联系人名称
	private String GENDER;// 性别
	private String TEL_TYPE;// 电话类型
	private String PHONE;// 电话号码
	private String IS_VALID; // 是否有效

	public String getCONTACT_ID() {
		return CONTACT_ID;
	}

	public void setCONTACT_ID(String cONTACT_ID) {
		CONTACT_ID = cONTACT_ID;
	}

	public String getCUST_ID() {
		return CUST_ID;
	}

	public void setCUST_ID(String cUST_ID) {
		CUST_ID = cUST_ID;
	}

	public String getRELATIONSHIP() {
		return RELATIONSHIP;
	}

	public void setRELATIONSHIP(String rELATIONSHIP) {
		RELATIONSHIP = rELATIONSHIP;
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

	public String getTEL_TYPE() {
		return TEL_TYPE;
	}

	public void setTEL_TYPE(String tEL_TYPE) {
		TEL_TYPE = tEL_TYPE;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getIS_VALID() {
		return IS_VALID;
	}

	public void setIS_VALID(String iS_VALID) {
		IS_VALID = iS_VALID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ClMobileMsg [CONTACT_ID=" + CONTACT_ID + ", CUST_ID=" + CUST_ID + ", RELATIONSHIP=" + RELATIONSHIP
				+ ", NAME=" + NAME + ", GENDER=" + GENDER + ", TEL_TYPE=" + TEL_TYPE + ", PHONE=" + PHONE
				+ ", IS_VALID=" + IS_VALID + "]";
	}

}
