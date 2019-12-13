package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

/**
 * 地址信息
 * 
 * @author 阳光保险
 *
 */
public class ClAddressDetails extends DataEntity<ClAddressDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ADDR_ID; // 地址ID
	private String CUST_ID;// 客户编号
	private String RELATIONSHIP;// 与持卡人关系
	private String NAME; // 联系人名称
	private String GENDER;// 性别
	private String POST;// 邮编
	private String ADDR_TYPE;// 地址类型
	private String IS_VALID; // 是否有效

	public String getADDR_ID() {
		return ADDR_ID;
	}

	public void setADDR_ID(String aDDR_ID) {
		ADDR_ID = aDDR_ID;
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

	public String getPOST() {
		return POST;
	}

	public void setPOST(String pOST) {
		POST = pOST;
	}

	public String getADDR_TYPE() {
		return ADDR_TYPE;
	}

	public void setADDR_TYPE(String aDDR_TYPE) {
		ADDR_TYPE = aDDR_TYPE;
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
		return "ClAddressMsg [ADDR_ID=" + ADDR_ID + ", CUST_ID=" + CUST_ID + ", RELATIONSHIP=" + RELATIONSHIP
				+ ", NAME=" + NAME + ", GENDER=" + GENDER + ", POST=" + POST + ", ADDR_TYPE=" + ADDR_TYPE
				+ ", IS_VALID=" + IS_VALID + "]";
	}

}
