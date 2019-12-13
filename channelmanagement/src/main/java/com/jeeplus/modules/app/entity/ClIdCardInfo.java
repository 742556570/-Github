package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClIdCardInfo extends DataEntity<ClIdCardInfo> {

	private Integer TRD_NO;
	private String USR_CDE;
	private String USR_TEL;
	private String CUST_NAME;
	private String ID_NO;
	private String INDIV_SEX;
	private String BIRTHDAY_DATE;
	private String INDIV_NATION;
	private String LIVE_ADDR;
	private String VALID_DATE1;
	private String VALID_DATE2;
	private String SIGN_DEPT;
	private String FORNT_IMGPTH;
	private String BACK_IMGPTH;
	private String FORNT_IMGPTH_LC;
	private String BACK_IMGPTH_LC;
	private String CRT_DT;
	private String MDF_DT;
	private String ISVALIDATE;
	private String DETAIL;

	public Integer getTRD_NO() {
		return TRD_NO;
	}

	public void setTRD_NO(Integer tRD_NO) {
		TRD_NO = tRD_NO;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}

	public String getID_NO() {
		return ID_NO;
	}

	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}

	public String getINDIV_SEX() {
		return INDIV_SEX;
	}

	public void setINDIV_SEX(String iNDIV_SEX) {
		INDIV_SEX = iNDIV_SEX;
	}

	public String getBIRTHDAY_DATE() {
		return BIRTHDAY_DATE;
	}

	public void setBIRTHDAY_DATE(String bIRTHDAY_DATE) {
		BIRTHDAY_DATE = bIRTHDAY_DATE;
	}

	public String getINDIV_NATION() {
		return INDIV_NATION;
	}

	public void setINDIV_NATION(String iNDIV_NATION) {
		INDIV_NATION = iNDIV_NATION;
	}

	public String getVALID_DATE1() {
		return VALID_DATE1;
	}

	public void setVALID_DATE1(String vALID_DATE1) {
		VALID_DATE1 = vALID_DATE1;
	}

	public String getVALID_DATE2() {
		return VALID_DATE2;
	}

	public void setVALID_DATE2(String vALID_DATE2) {
		VALID_DATE2 = vALID_DATE2;
	}

	public String getSIGN_DEPT() {
		return SIGN_DEPT;
	}

	public void setSIGN_DEPT(String sIGN_DEPT) {
		SIGN_DEPT = sIGN_DEPT;
	}

	public String getFORNT_IMGPTH() {
		return FORNT_IMGPTH;
	}

	public void setFORNT_IMGPTH(String fORNT_IMGPTH) {
		FORNT_IMGPTH = fORNT_IMGPTH;
	}

	public String getBACK_IMGPTH() {
		return BACK_IMGPTH;
	}

	public void setBACK_IMGPTH(String bACK_IMGPTH) {
		BACK_IMGPTH = bACK_IMGPTH;
	}

	public String getFORNT_IMGPTH_LC() {
		return FORNT_IMGPTH_LC;
	}

	public void setFORNT_IMGPTH_LC(String fORNT_IMGPTH_LC) {
		FORNT_IMGPTH_LC = fORNT_IMGPTH_LC;
	}

	public String getBACK_IMGPTH_LC() {
		return BACK_IMGPTH_LC;
	}

	public void setBACK_IMGPTH_LC(String bACK_IMGPTH_LC) {
		BACK_IMGPTH_LC = bACK_IMGPTH_LC;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	public String getMDF_DT() {
		return MDF_DT;
	}

	public void setMDF_DT(String mDF_DT) {
		MDF_DT = mDF_DT;
	}

	public String getISVALIDATE() {
		return ISVALIDATE;
	}

	public void setISVALIDATE(String iSVALIDATE) {
		ISVALIDATE = iSVALIDATE;
	}


	public String getLIVE_ADDR() {
		return LIVE_ADDR;
	}

	public void setLIVE_ADDR(String lIVE_ADDR) {
		LIVE_ADDR = lIVE_ADDR;
	}

	public String getDETAIL() {
		return DETAIL;
	}

	public void setDETAIL(String dETAIL) {
		DETAIL = dETAIL;
	}

	public String getUSR_TEL() {
		return USR_TEL;
	}

	public void setUSR_TEL(String uSR_TEL) {
		USR_TEL = uSR_TEL;
	}

	public ClIdCardInfo() {
		super();
	}

	@Override
	public String toString() {
		return "ClIdCardInfo [TRD_NO=" + TRD_NO + ", USR_CDE=" + USR_CDE + ", USR_TEL=" + USR_TEL + ", CUST_NAME="
				+ CUST_NAME + ", ID_NO=" + ID_NO + ", INDIV_SEX=" + INDIV_SEX + ", BIRTHDAY_DATE=" + BIRTHDAY_DATE
				+ ", INDIV_NATION=" + INDIV_NATION + ", LIVE_ADDR=" + LIVE_ADDR + ", VALID_DATE1=" + VALID_DATE1
				+ ", VALID_DATE2=" + VALID_DATE2 + ", SIGN_DEPT=" + SIGN_DEPT + ", FORNT_IMGPTH=" + FORNT_IMGPTH
				+ ", BACK_IMGPTH=" + BACK_IMGPTH + ", FORNT_IMGPTH_LC=" + FORNT_IMGPTH_LC + ", BACK_IMGPTH_LC="
				+ BACK_IMGPTH_LC + ", CRT_DT=" + CRT_DT + ", MDF_DT=" + MDF_DT + ", ISVALIDATE=" + ISVALIDATE
				+ ", DETAIL=" + DETAIL + "]";
	}


	



}
