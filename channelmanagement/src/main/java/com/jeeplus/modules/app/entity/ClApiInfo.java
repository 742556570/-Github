package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClApiInfo extends DataEntity<ClApiInfo>{

	private int APIINF_SEQ;
	private String USR_CDE;
	private String USR_TEL;
	private String INTERFACE_NAME;
	private String SERVICE_NAME;
	private String INVOKE_DT;
	private int TIME_SPAN;
	private String PARAMS;
	private String STATUS;
	private String RESULT;


	public int getAPIINF_SEQ() {
		return APIINF_SEQ;
	}

	public void setAPIINF_SEQ(int aPIINF_SEQ) {
		APIINF_SEQ = aPIINF_SEQ;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getUSR_TEL() {
		return USR_TEL;
	}

	public void setUSR_TEL(String uSR_TEL) {
		USR_TEL = uSR_TEL;
	}

	public String getINTERFACE_NAME() {
		return INTERFACE_NAME;
	}

	public void setINTERFACE_NAME(String iNTERFACE_NAME) {
		INTERFACE_NAME = iNTERFACE_NAME;
	}

	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}

	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}

	public String getINVOKE_DT() {
		return INVOKE_DT;
	}

	public void setINVOKE_DT(String iNVOKE_DT) {
		INVOKE_DT = iNVOKE_DT;
	}

	public int getTIME_SPAN() {
		return TIME_SPAN;
	}

	public void setTIME_SPAN(int tIME_SPAN) {
		TIME_SPAN = tIME_SPAN;
	}

	public String getPARAMS() {
		return PARAMS;
	}

	public void setPARAMS(String pARAMS) {
		PARAMS = pARAMS;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getRESULT() {
		return RESULT;
	}

	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}

	public ClApiInfo() {
		super();
	}
	public ClApiInfo( String uSR_CDE, String uSR_TEL, String iNTERFACE_NAME, String sERVICE_NAME,
			String iNVOKE_DT, int tIME_SPAN, String pARAMS, String sTATUS, String rESULT) {
		super();
		USR_CDE = uSR_CDE;
		USR_TEL = uSR_TEL;
		INTERFACE_NAME = iNTERFACE_NAME;
		SERVICE_NAME = sERVICE_NAME;
		INVOKE_DT = iNVOKE_DT;
		TIME_SPAN = tIME_SPAN;
		PARAMS = pARAMS;
		STATUS = sTATUS;
		RESULT = rESULT;
	}

	public ClApiInfo(int exApi_SEQ, String uSR_CDE, String uSR_TEL, String iNTERFACE_NAME, String sERVICE_NAME,
			String iNVOKE_DT, int tIME_SPAN, String pARAMS, String sTATUS, String rESULT) {
		super();
		APIINF_SEQ = exApi_SEQ;
		USR_CDE = uSR_CDE;
		USR_TEL = uSR_TEL;
		INTERFACE_NAME = iNTERFACE_NAME;
		SERVICE_NAME = sERVICE_NAME;
		INVOKE_DT = iNVOKE_DT;
		TIME_SPAN = tIME_SPAN;
		PARAMS = pARAMS;
		STATUS = sTATUS;
		RESULT = rESULT;
	}

	@Override
	public String toString() {
		return "ClExtenalApi [APIINF_SEQ=" + APIINF_SEQ + ", USR_CDE=" + USR_CDE + ", USR_TEL=" + USR_TEL
				+ ", INTERFACE_NAME=" + INTERFACE_NAME + ", SERVICE_NAME=" + SERVICE_NAME + ", INVOKE_DT=" + INVOKE_DT
				+ ", TIME_SPAN=" + TIME_SPAN + ", PARAMS=" + PARAMS + ", STATUS=" + STATUS + ", RESULT=" + RESULT + "]";
	}

}
