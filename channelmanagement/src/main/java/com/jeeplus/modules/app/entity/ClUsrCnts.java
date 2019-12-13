package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClUsrCnts extends DataEntity<ClUsrCnts> {

	private Integer CNTS_SEQ;
	private String USR_CDE;
	private String REL_RELATION;
	private String REL_NAME;
	private String REL_MOBILE;
	private String LST_MDF_DAT;
	private String CRT_DT;
	private String MDF_DT;
	private String IS_DSPL;
	private String LVL;
	private String RMK;

	public Integer getCNTS_SEQ() {
		return CNTS_SEQ;
	}

	public void setCNTS_SEQ(Integer cNTS_SEQ) {
		CNTS_SEQ = cNTS_SEQ;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getREL_RELATION() {
		return REL_RELATION;
	}

	public void setREL_RELATION(String rEL_RELATION) {
		REL_RELATION = rEL_RELATION;
	}

	public String getREL_NAME() {
		return REL_NAME;
	}

	public void setREL_NAME(String rEL_NAME) {
		REL_NAME = rEL_NAME;
	}

	public String getREL_MOBILE() {
		return REL_MOBILE.replaceAll("-", "").replaceAll("\\s*", "");
	}

	public void setREL_MOBILE(String rEL_MOBILE) {
		REL_MOBILE = rEL_MOBILE;
	}

	public String getLST_MDF_DAT() {
		return LST_MDF_DAT;
	}

	public void setLST_MDF_DAT(String lST_MDF_DAT) {
		LST_MDF_DAT = lST_MDF_DAT;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	public String getRMK() {
		return RMK;
	}

	public void setRMK(String rMK) {
		RMK = rMK;
	}

	public String getMDF_DT() {
		return MDF_DT;
	}

	public void setMDF_DT(String mDF_DT) {
		MDF_DT = mDF_DT;
	}

	public String getIS_DSPL() {
		return IS_DSPL;
	}

	public void setIS_DSPL(String iS_DSPL) {
		IS_DSPL = iS_DSPL;
	}

	public String getLVL() {
		return LVL;
	}

	public void setLVL(String lVL) {
		LVL = lVL;
	}

	public ClUsrCnts() {
		super();
	}

	public ClUsrCnts(Integer cNTS_SEQ, String uSR_CDE, String rEL_RELATION, String rEL_NAME, String rEL_MOBILE,
			String lST_MDF_DAT, String cRT_DT, String mDF_DT, String iS_DSPL, String lVL, String rMK) {
		super();
		CNTS_SEQ = cNTS_SEQ;
		USR_CDE = uSR_CDE;
		REL_RELATION = rEL_RELATION;
		REL_NAME = rEL_NAME;
		REL_MOBILE = rEL_MOBILE;
		LST_MDF_DAT = lST_MDF_DAT;
		CRT_DT = cRT_DT;
		MDF_DT = mDF_DT;
		IS_DSPL = iS_DSPL;
		LVL = lVL;
		RMK = rMK;
	}

	@Override
	public String toString() {
		return "ClUsrCnts [CNTS_SEQ=" + CNTS_SEQ + ", USR_CDE=" + USR_CDE + ", REL_RELATION=" + REL_RELATION
				+ ", REL_NAME=" + REL_NAME + ", REL_MOBILE=" + REL_MOBILE + ", LST_MDF_DAT=" + LST_MDF_DAT + ", CRT_DT="
				+ CRT_DT + ", MDF_DT=" + MDF_DT + ", IS_DSPL=" + IS_DSPL + ", LVL=" + LVL + ", RMK=" + RMK + "]";
	}

	

}
