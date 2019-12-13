package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClCrashLog extends DataEntity<ClCrashLog> {

	private int LOG_ID;
	// 平台
	private String PLATFORM;
	// 品牌
	private String BRAND;
	// 设备型号
	private String MODEL;
	// 定制系统名称
	private String DISPLAY;
	// 系统版本
	private String OS_VERSION;
	// app版本
	private String APP_VERSION;
	// 时间
	private String CRT_DT;
	// 崩溃日志
	private String CRASH_LOG;

	public int getLOG_ID() {
		return LOG_ID;
	}

	public void setLOG_ID(int lOG_ID) {
		LOG_ID = lOG_ID;
	}

	public String getPLATFORM() {
		return PLATFORM;
	}

	public void setPLATFORM(String pLATFORM) {
		PLATFORM = pLATFORM;
	}

	public String getBRAND() {
		return BRAND;
	}

	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}

	public String getMODEL() {
		return MODEL;
	}

	public void setMODEL(String mODEL) {
		MODEL = mODEL;
	}

	public String getDISPLAY() {
		return DISPLAY;
	}

	public void setDISPLAY(String dISPLAY) {
		DISPLAY = dISPLAY;
	}

	public String getOS_VERSION() {
		return OS_VERSION;
	}

	public void setOS_VERSION(String oS_VERSION) {
		OS_VERSION = oS_VERSION;
	}

	public String getAPP_VERSION() {
		return APP_VERSION;
	}

	public void setAPP_VERSION(String aPP_VERSION) {
		APP_VERSION = aPP_VERSION;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	public String getCRASH_LOG() {
		return CRASH_LOG;
	}

	public void setCRASH_LOG(String cRASH_LOG) {
		CRASH_LOG = cRASH_LOG;
	}

	public ClCrashLog() {
		super();
	}

	public ClCrashLog(int lOG_ID, String pLATFORM, String bRAND, String mODEL, String dISPLAY, String oS_VERSION,
			String aPP_VERSION, String cRT_DT, String cRASH_LOG) {
		super();
		LOG_ID = lOG_ID;
		PLATFORM = pLATFORM;
		BRAND = bRAND;
		MODEL = mODEL;
		DISPLAY = dISPLAY;
		OS_VERSION = oS_VERSION;
		APP_VERSION = aPP_VERSION;
		CRT_DT = cRT_DT;
		CRASH_LOG = cRASH_LOG;
	}

	public ClCrashLog(String pLATFORM, String bRAND, String mODEL, String dISPLAY, String oS_VERSION,
			String aPP_VERSION, String cRT_DT, String cRASH_LOG) {
		super();
		PLATFORM = pLATFORM;
		BRAND = bRAND;
		MODEL = mODEL;
		DISPLAY = dISPLAY;
		OS_VERSION = oS_VERSION;
		APP_VERSION = aPP_VERSION;
		CRT_DT = cRT_DT;
		CRASH_LOG = cRASH_LOG;
	}

	@Override
	public String toString() {
		return "ClCrashLog [LOG_ID=" + LOG_ID + ", PLATFORM=" + PLATFORM + ", BRAND=" + BRAND + ", MODEL=" + MODEL
				+ ", DISPLAY=" + DISPLAY + ", OS_VERSION=" + OS_VERSION + ", APP_VERSION=" + APP_VERSION + ", CRT_DT="
				+ CRT_DT + ", CRASH_LOG=" + CRASH_LOG + "]";
	}

}
