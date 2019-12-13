package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ClCrdtExt extends DataEntity<ClCrdtExt> {

	private int CRDTEXT_SEQ;
	private String USR_APPSEQ;
	private String USR_CDE;
	private String PRD_CDE;
	private BigDecimal ADJ_AMT_MIN;
	private BigDecimal ADJ_AMT_MAX;
	private BigDecimal ADJ_AMT;
	private BigDecimal ADJ_RAT;
	private String CRDTEXT_DT;
	private String STATUS;
	private String ST_CDE;
	private String DETAIL;
	private String ADM_USR;
	private String CRT_DT;
	private String MDF_DT;
	private String CRDTEXT_TYPE = "innerWhiteList";
	private String IS_EFF = "true";

	public int getCRDTEXT_SEQ() {
		return CRDTEXT_SEQ;
	}

	public void setCRDTEXT_SEQ(int cRDTEXT_SEQ) {
		CRDTEXT_SEQ = cRDTEXT_SEQ;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getPRD_CDE() {
		return PRD_CDE;
	}

	public void setPRD_CDE(String pRD_CDE) {
		PRD_CDE = pRD_CDE;
	}

	public BigDecimal getADJ_AMT_MIN() {
		return ADJ_AMT_MIN;
	}

	public void setADJ_AMT_MIN(BigDecimal aDJ_AMT_MIN) {
		ADJ_AMT_MIN = aDJ_AMT_MIN;
	}

	public BigDecimal getADJ_AMT_MAX() {
		return ADJ_AMT_MAX;
	}

	public void setADJ_AMT_MAX(BigDecimal aDJ_AMT_MAX) {
		ADJ_AMT_MAX = aDJ_AMT_MAX;
	}

	public BigDecimal getADJ_RAT() {
		return ADJ_RAT;
	}

	public void setADJ_RAT(BigDecimal aDJ_RAT) {
		ADJ_RAT = aDJ_RAT;
	}

	public String getCRDTEXT_DT() {
		return CRDTEXT_DT;
	}

	public void setCRDTEXT_DT(String cRDTEXT_DT) {
		CRDTEXT_DT = cRDTEXT_DT;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getADM_USR() {
		return ADM_USR;
	}

	public void setADM_USR(String aDM_USR) {
		ADM_USR = aDM_USR;
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

	public String getDETAIL() {
		return DETAIL;
	}

	public void setDETAIL(String dETAIL) {
		DETAIL = dETAIL;
	}

	public BigDecimal getADJ_AMT() {
		return ADJ_AMT;
	}

	public void setADJ_AMT(BigDecimal aDJ_AMT) {
		ADJ_AMT = aDJ_AMT;
	}

	public String getUSR_APPSEQ() {
		return USR_APPSEQ;
	}

	public void setUSR_APPSEQ(String uSR_APPSEQ) {
		USR_APPSEQ = uSR_APPSEQ;
	}
	
	public String getCRDTEXT_TYPE() {
		return CRDTEXT_TYPE;
	}

	public void setCRDTEXT_TYPE(String cRDTEXT_TYPE) {
		CRDTEXT_TYPE = cRDTEXT_TYPE;
	}

	public String getST_CDE() {
		return ST_CDE;
	}

	public void setST_CDE(String sT_CDE) {
		ST_CDE = sT_CDE;
	}
	
	public String getIS_EFF() {
		return IS_EFF;
	}

	public void setIS_EFF(String iS_EFF) {
		IS_EFF = iS_EFF;
	}

	public ClCrdtExt() {
		super();
	}

	
	public ClCrdtExt(int cRDTEXT_SEQ, String uSR_APPSEQ, String uSR_CDE, String pRD_CDE, BigDecimal aDJ_AMT_MIN,
			BigDecimal aDJ_AMT_MAX, BigDecimal aDJ_AMT, BigDecimal aDJ_RAT, String cRDTEXT_DT, String sTATUS,
			String dETAIL, String aDM_USR, String cRT_DT, String mDF_DT) {
		super();
		CRDTEXT_SEQ = cRDTEXT_SEQ;
		USR_APPSEQ = uSR_APPSEQ;
		USR_CDE = uSR_CDE;
		PRD_CDE = pRD_CDE;
		ADJ_AMT_MIN = aDJ_AMT_MIN;
		ADJ_AMT_MAX = aDJ_AMT_MAX;
		ADJ_AMT = aDJ_AMT;
		ADJ_RAT = aDJ_RAT;
		CRDTEXT_DT = cRDTEXT_DT;
		STATUS = sTATUS;
		DETAIL = dETAIL;
		ADM_USR = aDM_USR;
		CRT_DT = cRT_DT;
		MDF_DT = mDF_DT;
	}

	@Override
	public String toString() {
		return "ClCrdtExt [CRDTEXT_SEQ=" + CRDTEXT_SEQ + ", USR_APPSEQ=" + USR_APPSEQ + ", USR_CDE=" + USR_CDE
				+ ", PRD_CDE=" + PRD_CDE + ", ADJ_AMT_MIN=" + ADJ_AMT_MIN + ", ADJ_AMT_MAX=" + ADJ_AMT_MAX
				+ ", ADJ_AMT=" + ADJ_AMT + ", ADJ_RAT=" + ADJ_RAT + ", CRDTEXT_DT=" + CRDTEXT_DT + ", STATUS=" + STATUS
				+ ", DETAIL=" + DETAIL + ", ADM_USR=" + ADM_USR + ", CRT_DT=" + CRT_DT + ", MDF_DT=" + MDF_DT + "]";
	}


	

}
