package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClSyncDx extends DataEntity<ClSyncDx> {

	private String USR_CDE;
	private String IS_SYNC;
	private String CRT_DT;

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getIS_SYNC() {
		return IS_SYNC;
	}

	public void setIS_SYNC(String iS_SYNC) {
		IS_SYNC = iS_SYNC;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}

	
}
