package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ChChannelInfo extends DataEntity<ChChannelInfo> {

	private String CH_SEQ;
	private String CH_CDE;
	private String CH_NAME;
	private String FA_CH_CDE;
	private String FA_CH_NAME;
	private String CH_LINK;
	private String CH_DAY_LIMIT;
	private String CH_STATUS;
	private String RMK;
	private String VERSION;

	public String getCH_SEQ() {
		return CH_SEQ;
	}

	public void setCH_SEQ(String cH_SEQ) {
		CH_SEQ = cH_SEQ;
	}

	public String getCH_CDE() {
		return CH_CDE;
	}

	public void setCH_CDEE(String cH_CDE) {
		CH_CDE = cH_CDE;
	}

	public String getCH_NAME() {
		return CH_NAME;
	}

	public void setCH_NAME(String cH_NAME) {
		CH_NAME = cH_NAME;
	}

	public String getFA_CH_CDE() {
		return FA_CH_CDE;
	}

	public void setFA_CH_CDE(String fA_CH_CDE) {
		FA_CH_CDE = fA_CH_CDE;
	}
	
	public String getFA_CH_NAME() {
		return FA_CH_NAME;
	}

	public void setFA_CH_NAME(String fA_CH_NAME) {
		FA_CH_NAME = fA_CH_NAME;
	}

	public String getCH_LINK() {
		return CH_LINK;
	}

	public void setCH_LINK(String cH_LINK) {
		CH_LINK = cH_LINK;
	}
	
	public String getCH_DAY_LIMIT() {
		return CH_DAY_LIMIT;
	}

	public void setCH_DAY_LIMIT(String cH_DAY_LIMIT) {
		CH_DAY_LIMIT = cH_DAY_LIMIT;
	}
	
	public String getCH_STATUS() {
		return CH_STATUS;
	}

	public void setCH_STATUS(String cH_STATUS) {
		CH_STATUS = cH_STATUS;
	}
	
	public String getRMK() {
		return RMK;
	}

	public void setRMK(String rMK) {
		RMK = rMK;
	}
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}
	
}
