package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClDealFailed extends DataEntity<ClDealFailed> {
	private String USR_CDE;
	private String POLICY_NO;
	private String FAIL_STEP;
	private String CRT_DT;
	private String ISRETRY;
	private String RETRY_DT;
	private Integer RETRY_TIMES;
	private String ISDONE;

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getPOLICY_NO() {
		return POLICY_NO;
	}

	public void setPOLICY_NO(String pOLICY_NO) {
		POLICY_NO = pOLICY_NO;
	}

	public String getFAIL_STEP() {
		return FAIL_STEP;
	}

	public void setFAIL_STEP(String fAIL_STEP) {
		FAIL_STEP = fAIL_STEP;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	public String getISRETRY() {
		return ISRETRY;
	}

	public void setISRETRY(String iSRETRY) {
		ISRETRY = iSRETRY;
	}

	public String getRETRY_DT() {
		return RETRY_DT;
	}

	public void setRETRY_DT(String rETRY_DT) {
		RETRY_DT = rETRY_DT;
	}

	public Integer getRETRY_TIMES() {
		return RETRY_TIMES;
	}

	public void setRETRY_TIMES(Integer rETRY_TIMES) {
		RETRY_TIMES = rETRY_TIMES;
	}

	public String getISDONE() {
		return ISDONE;
	}

	public void setISDONE(String iSDONE) {
		ISDONE = iSDONE;
	}

	@Override
	public String toString() {
		return "ClDealFailed [USR_CDE=" + USR_CDE + ", POLICY_NO=" + POLICY_NO + ", FAIL_STEP=" + FAIL_STEP
				+ ", CRT_DT=" + CRT_DT + ", ISRETRY=" + ISRETRY + ", RETRY_DT=" + RETRY_DT + ", RETRY_TIMES="
				+ RETRY_TIMES + ", ISDONE=" + ISDONE + "]";
	}

}
