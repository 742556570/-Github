package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ClPrdInfo extends DataEntity<ClPrdInfo> {

	private String PRD_SEQ;
	private String PRD_CDE;
	private String PRD_NAME;
	private String PRD_MEMO;
	private String PRD_TYPE;
	private BigDecimal MIN_LOAN_AMT;
	private BigDecimal MAX_LOAN_AMT;
	private String PAY_TERM;
	private String REPAY_MODE;
	private String GL_RAT_BASE;
	private String GL_DUE_DAY_OPT;
	private BigDecimal INT_RAT;
	private BigDecimal INT_RAT_ED;
	private BigDecimal GL_OD_RATE;
	private BigDecimal GL_OD_MIN_AMT;
	private String GL_OD_COMM_PART;
	private BigDecimal PREMIUM_RATE;
	private BigDecimal POLICY_AMT;
	private BigDecimal OVER_FEE_AMT;
	private BigDecimal FEE_AMT;
	private String CAP_CDE;
	private String CRT_DT;
	private String CRT_USR;
	private String MDF_DT;
	private String MDF_USR;

	public String getPRD_SEQ() {
		return PRD_SEQ;
	}

	public void setPRD_SEQ(String pRD_SEQ) {
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

	public String getPRD_TYPE() {
		return PRD_TYPE;
	}

	public void setPRD_TYPE(String pRD_TYPE) {
		PRD_TYPE = pRD_TYPE;
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

	public String getREPAY_MODE() {
		return REPAY_MODE;
	}

	public void setREPAY_MODE(String rEPAY_MODE) {
		REPAY_MODE = rEPAY_MODE;
	}

	public String getGL_RAT_BASE() {
		return GL_RAT_BASE;
	}

	public void setGL_RAT_BASE(String gL_RAT_BASE) {
		GL_RAT_BASE = gL_RAT_BASE;
	}

	public String getGL_DUE_DAY_OPT() {
		return GL_DUE_DAY_OPT;
	}

	public void setGL_DUE_DAY_OPT(String gL_DUE_DAY_OPT) {
		GL_DUE_DAY_OPT = gL_DUE_DAY_OPT;
	}

	public BigDecimal getINT_RAT() {
		return INT_RAT;
	}

	public void setINT_RAT(BigDecimal iNT_RAT) {
		INT_RAT = iNT_RAT;
	}

	public BigDecimal getGL_OD_RATE() {
		return GL_OD_RATE;
	}

	public void setGL_OD_RATE(BigDecimal gL_OD_RATE) {
		GL_OD_RATE = gL_OD_RATE;
	}

	public BigDecimal getGL_OD_MIN_AMT() {
		return GL_OD_MIN_AMT;
	}

	public void setGL_OD_MIN_AMT(BigDecimal gL_OD_MIN_AMT) {
		GL_OD_MIN_AMT = gL_OD_MIN_AMT;
	}

	public String getGL_OD_COMM_PART() {
		return GL_OD_COMM_PART;
	}

	public void setGL_OD_COMM_PART(String gL_OD_COMM_PART) {
		GL_OD_COMM_PART = gL_OD_COMM_PART;
	}

	public BigDecimal getPREMIUM_RATE() {
		return PREMIUM_RATE;
	}

	public void setPREMIUM_RATE(BigDecimal pREMIUM_RATE) {
		PREMIUM_RATE = pREMIUM_RATE;
	}

	public BigDecimal getPOLICY_AMT() {
		return POLICY_AMT;
	}

	public void setPOLICY_AMT(BigDecimal pOLICY_AMT) {
		POLICY_AMT = pOLICY_AMT;
	}

	public BigDecimal getOVER_FEE_AMT() {
		return OVER_FEE_AMT;
	}

	public void setOVER_FEE_AMT(BigDecimal oVER_FEE_AMT) {
		OVER_FEE_AMT = oVER_FEE_AMT;
	}

	public BigDecimal getFEE_AMT() {
		return FEE_AMT;
	}

	public void setFEE_AMT(BigDecimal fEE_AMT) {
		FEE_AMT = fEE_AMT;
	}
	
	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	public String getCRT_USR() {
		return CRT_USR;
	}

	public void setCRT_USR(String cRT_USR) {
		CRT_USR = cRT_USR;
	}

	public String getMDF_DT() {
		return MDF_DT;
	}

	public void setMDF_DT(String mDF_DT) {
		MDF_DT = mDF_DT;
	}

	public String getMDF_USR() {
		return MDF_USR;
	}

	public void setMDF_USR(String mDF_USR) {
		MDF_USR = mDF_USR;
	}

	public String getCAP_CDE() {
		return CAP_CDE;
	}

	public void setCAP_CDE(String cAP_CDE) {
		CAP_CDE = cAP_CDE;
	}

	public BigDecimal getINT_RAT_ED() {
		return INT_RAT_ED;
	}

	public void setINT_RAT_ED(BigDecimal iNT_RAT_ED) {
		INT_RAT_ED = iNT_RAT_ED;
	}


	

	
	
}
