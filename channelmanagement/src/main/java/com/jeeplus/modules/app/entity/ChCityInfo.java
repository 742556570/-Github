package com.jeeplus.modules.app.entity;

import java.math.BigDecimal;

import com.jeeplus.common.persistence.DataEntity;

public class ChCityInfo extends DataEntity<ChCityInfo> {

	private String CITY_SEQ;
	private String CITY_CDE;
	private String CITY_NAME;
	private String CITY_TYPE;
	private String CITY_PINYIN;

	public String getCITY_SEQ() {
		return CITY_SEQ;
	}

	public void setCITY_SEQ(String cITY_SEQ) {
		CITY_SEQ = cITY_SEQ;
	}

	public String getCITY_CDE() {
		return CITY_CDE;
	}

	public void setCITY_CDE(String cITY_CDE) {
		CITY_CDE = cITY_CDE;
	}

	public String getCITY_NAME() {
		return CITY_NAME;
	}

	public void setCITY_NAMEE(String cITY_NAME) {
		CITY_NAME = cITY_NAME;
	}

	public String getCITY_TYPE() {
		return CITY_TYPE;
	}

	public void setCITY_TYPE(String cITY_TYPE) {
		CITY_TYPE = cITY_TYPE;
	}
	
	public String getCITY_PINYIN() {
		return CITY_PINYIN;
	}

	public void setCITY_PINYIN(String cITY_PINYIN) {
		CITY_PINYIN = cITY_PINYIN;
	}
	
}
