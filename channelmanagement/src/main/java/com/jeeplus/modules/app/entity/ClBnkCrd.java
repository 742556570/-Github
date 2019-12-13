package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClBnkCrd extends DataEntity<ClBnkCrd> {
	private Integer BCRD_SEQ;
	private String USR_CDE;
	private String APPL_BANK_NAME;
	private String BNK_CODE;
	private String APPL_AC_BANK;
	private String ET_BNK_ADDR;
	private String CARDTYPE;
	private String APPL_CARD_NO;
	private String EPR_DT;
	private String SGN_CDE;
	private String APPL_AC_NAM;
	private String APPL_ID_NO;
	private String USR_TEL;
	private String STATUS;
	private String IS_DEF;
	private String IS_DEL;
	private Integer WGHT;
	private String IS_BIND;
	private String BIND_DT;
	private String BIND_IP;
	private String BIND_CDE;
	private String MDF_DT;
	private String IS_BINDFOTIC;

	public Integer getBCRD_SEQ() {
		return BCRD_SEQ;
	}

	public void setBCRD_SEQ(Integer bCRD_SEQ) {
		BCRD_SEQ = bCRD_SEQ;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getAPPL_BANK_NAME() {
		return APPL_BANK_NAME;
	}

	public void setAPPL_BANK_NAME(String aPPL_BANK_NAME) {
		APPL_BANK_NAME = aPPL_BANK_NAME;
	}

	public String getBNK_CODE() {
		return BNK_CODE;
	}

	public void setBNK_CODE(String bNK_CODE) {
		BNK_CODE = bNK_CODE;
	}

	public String getAPPL_AC_BANK() {
		return APPL_AC_BANK;
	}

	public void setAPPL_AC_BANK(String aPPL_AC_BANK) {
		APPL_AC_BANK = aPPL_AC_BANK;
	}

	public String getET_BNK_ADDR() {
		return ET_BNK_ADDR;
	}

	public void setET_BNK_ADDR(String eT_BNK_ADDR) {
		ET_BNK_ADDR = eT_BNK_ADDR;
	}

	public String getCARDTYPE() {
		return CARDTYPE;
	}

	public void setCARDTYPE(String cARDTYPE) {
		CARDTYPE = cARDTYPE;
	}

	public String getAPPL_CARD_NO() {
		return APPL_CARD_NO;
	}

	public void setAPPL_CARD_NO(String aPPL_CARD_NO) {
		APPL_CARD_NO = aPPL_CARD_NO;
	}

	public String getEPR_DT() {
		return EPR_DT;
	}

	public void setEPR_DT(String ePR_DT) {
		EPR_DT = ePR_DT;
	}

	public String getSGN_CDE() {
		return SGN_CDE;
	}

	public void setSGN_CDE(String sGN_CDE) {
		SGN_CDE = sGN_CDE;
	}

	public String getAPPL_AC_NAM() {
		return APPL_AC_NAM;
	}

	public void setAPPL_AC_NAM(String aPPL_AC_NAM) {
		APPL_AC_NAM = aPPL_AC_NAM;
	}

	public String getAPPL_ID_NO() {
		return APPL_ID_NO;
	}

	public void setAPPL_ID_NO(String aPPL_ID_NO) {
		APPL_ID_NO = aPPL_ID_NO;
	}

	public String getUSR_TEL() {
		return USR_TEL;
	}

	public void setUSR_TEL(String uSR_TEL) {
		USR_TEL = uSR_TEL;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getIS_DEF() {
		return IS_DEF;
	}

	public void setIS_DEF(String iS_DEF) {
		IS_DEF = iS_DEF;
	}

	public String getIS_DEL() {
		return IS_DEL;
	}

	public void setIS_DEL(String iS_DEL) {
		IS_DEL = iS_DEL;
	}

	public Integer getWGHT() {
		return WGHT;
	}

	public void setWGHT(Integer wGHT) {
		WGHT = wGHT;
	}

	public String getIS_BIND() {
		return IS_BIND;
	}

	public void setIS_BIND(String iS_BIND) {
		IS_BIND = iS_BIND;
	}

	public String getBIND_DT() {
		return BIND_DT;
	}

	public void setBIND_DT(String bIND_DT) {
		BIND_DT = bIND_DT;
	}

	public String getBIND_IP() {
		return BIND_IP;
	}

	public void setBIND_IP(String bIND_IP) {
		BIND_IP = bIND_IP;
	}

	public String getBIND_CDE() {
		return BIND_CDE;
	}

	public void setBIND_CDE(String bIND_CDE) {
		BIND_CDE = bIND_CDE;
	}

	public String getMDF_DT() {
		return MDF_DT;
	}

	public void setMDF_DT(String mDF_DT) {
		MDF_DT = mDF_DT;
	}

	public String getIS_BINDFOTIC() {
		return IS_BINDFOTIC;
	}

	public void setIS_BINDFOTIC(String IS_BINDFOTIC) {
		this.IS_BINDFOTIC = IS_BINDFOTIC;
	}

	public ClBnkCrd() {
		super();
	}


	@java.lang.Override
	public java.lang.String toString() {
		return "ClBnkCrd{" +
				"BCRD_SEQ=" + BCRD_SEQ +
				", USR_CDE='" + USR_CDE + '\'' +
				", APPL_BANK_NAME='" + APPL_BANK_NAME + '\'' +
				", BNK_CODE='" + BNK_CODE + '\'' +
				", APPL_AC_BANK='" + APPL_AC_BANK + '\'' +
				", ET_BNK_ADDR='" + ET_BNK_ADDR + '\'' +
				", CARDTYPE='" + CARDTYPE + '\'' +
				", APPL_CARD_NO='" + APPL_CARD_NO + '\'' +
				", EPR_DT='" + EPR_DT + '\'' +
				", SGN_CDE='" + SGN_CDE + '\'' +
				", APPL_AC_NAM='" + APPL_AC_NAM + '\'' +
				", APPL_ID_NO='" + APPL_ID_NO + '\'' +
				", USR_TEL='" + USR_TEL + '\'' +
				", STATUS='" + STATUS + '\'' +
				", IS_DEF='" + IS_DEF + '\'' +
				", IS_DEL='" + IS_DEL + '\'' +
				", WGHT=" + WGHT +
				", IS_BIND='" + IS_BIND + '\'' +
				", BIND_DT='" + BIND_DT + '\'' +
				", BIND_IP='" + BIND_IP + '\'' +
				", BIND_CDE='" + BIND_CDE + '\'' +
				", MDF_DT='" + MDF_DT + '\'' +
				", IS_BINDFOTIC='" + IS_BINDFOTIC + '\'' +
				'}';
	}
}