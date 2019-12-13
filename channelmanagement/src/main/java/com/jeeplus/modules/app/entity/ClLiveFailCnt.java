package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClLiveFailCnt extends DataEntity<ClLiveFailCnt> {

	private String RESULT;
	private String CNT;

	public String getRESULT() {
		return RESULT;
	}

	public void setRESULT(String rESULT) {
		RESULT = rESULT;
	}

	public String getCNT() {
		return CNT;
	}

	public void setCNT(String cNT) {
		CNT = cNT;
	}

	public ClLiveFailCnt() {
		super();
	}

	public ClLiveFailCnt(String rESULT, String cNT) {
		super();
		RESULT = rESULT;
		CNT = cNT;
	}

	@Override
	public String toString() {
		return "ClLiveFailCnt [RESULT=" + RESULT + ", CNT=" + CNT + "]";
	}

}
