package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ChPrdInfo extends DataEntity<ChPrdInfo> {

	private int PRD_SEQ;
	private String PRD_CDE;
	private String PRD_NAME;
	private String PRD_MEMO;
	private String PRD_CDE_SUP;
	private String PRD_NAME_SUP;
	private BigDecimal MIN_LOAN_AMT;
	private BigDecimal MAX_LOAN_AMT;
	private String PAY_TERM;
	

	public int getPRD_SEQ() {
		return PRD_SEQ;
	}

	public void setPRD_SEQ(int pRD_SEQ) {
		PRD_SEQ = pRD_SEQ;
	}

	public String getPRD_CDE() {
		return PRD_CDE;
	}

	public void setPRD_CDE(String pRD_CDE) {
		PRD_CDE = pRD_CDE;
	}

	public String getPRD_NAME() {
		return PRD_NAME;
	}

	public void setPRD_NAME(String pRD_NAME) {
		PRD_NAME = pRD_NAME;
	}

	public String getPRD_MEMO() {
		return PRD_MEMO;
	}

	public void setPRD_MEMO(String pRD_MEMO) {
		PRD_MEMO = pRD_MEMO;
	}
	public String getPRD_CDE_SUP() {
		return PRD_CDE_SUP;
	}

	public void setPRD_CDE_SUP(String pRD_CDE_SUP) {
		PRD_CDE_SUP = pRD_CDE_SUP;
	}
	public String getPRD_NAME_SUP() {
		return PRD_NAME_SUP;
	}

	public void setPRD_NAME_SUP(String pRD_NAME_SUP) {
		PRD_NAME_SUP = pRD_NAME_SUP;
	}

	public BigDecimal getMIN_LOAN_AMT() {
		return MIN_LOAN_AMT;
	}

	public void setMIN_LOAN_AMT(BigDecimal mIN_LOAN_AMT) {
		MIN_LOAN_AMT = mIN_LOAN_AMT;
	}

	public BigDecimal getMAX_LOAN_AMT() {
		return MAX_LOAN_AMT;
	}

	public void setMAX_LOAN_AMT(BigDecimal mAX_LOAN_AMT) {
		MAX_LOAN_AMT = mAX_LOAN_AMT;
	}

	public String getPAY_TERM() {
		return PAY_TERM;
	}

	public void setPAY_TERM(String pAY_TERM) {
		PAY_TERM = pAY_TERM;
	}

	
}
