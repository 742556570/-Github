package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ClPremiumDetail extends DataEntity<ClPremiumDetail> {
	private String PRD_CDE;
	private String PAY_TERM;
	private BigDecimal PREMIUM_RATE;

	public String getPRD_CDE() {
		return PRD_CDE;
	}

	public void setPRD_CDE(String pRD_CDE) {
		PRD_CDE = pRD_CDE;
	}

	public String getPAY_TERM() {
		return PAY_TERM;
	}

	public void setPAY_TERM(String pAY_TERM) {
		PAY_TERM = pAY_TERM;
	}

	public BigDecimal getPREMIUM_RATE() {
		return PREMIUM_RATE;
	}

	public void setPREMIUM_RATE(BigDecimal pREMIUM_RATE) {
		PREMIUM_RATE = pREMIUM_RATE;
	}

}
