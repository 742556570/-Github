package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClIdImg extends DataEntity<ClIdImg>{

	private int IMG_SEQ;
	private String IMG_TRDNO;
	private String USR_CDE;
	private String IMG_PATH_LC;
	private String IMG_TYPE;
	private String IMG_FEATURE;
	private String CRT_DT;

	public int getIMG_SEQ() {
		return IMG_SEQ;
	}

	public void setIMG_SEQ(int iMG_SEQ) {
		IMG_SEQ = iMG_SEQ;
	}

	public String getIMG_TRDNO() {
		return IMG_TRDNO;
	}

	public void setIMG_TRDNO(String iMG_TRDNO) {
		IMG_TRDNO = iMG_TRDNO;
	}

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		USR_CDE = uSR_CDE;
	}

	public String getIMG_PATH_LC() {
		return IMG_PATH_LC;
	}

	public void setIMG_PATH_LC(String iMG_PATH_LC) {
		IMG_PATH_LC = iMG_PATH_LC;
	}

	public String getIMG_TYPE() {
		return IMG_TYPE;
	}

	public void setIMG_TYPE(String iMG_TYPE) {
		IMG_TYPE = iMG_TYPE;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		CRT_DT = cRT_DT;
	}
	
	public String getIMG_FEATURE() {
		return IMG_FEATURE;
	}

	public void setIMG_FEATURE(String iMG_FEATURE) {
		IMG_FEATURE = iMG_FEATURE;
	}

	public ClIdImg() {
		super();
	}

	
	
	public ClIdImg(String iMG_TRDNO, String uSR_CDE, String iMG_PATH_LC, String iMG_TYPE, String cRT_DT) {
		super();
		IMG_TRDNO = iMG_TRDNO;
		USR_CDE = uSR_CDE;
		IMG_PATH_LC = iMG_PATH_LC;
		IMG_TYPE = iMG_TYPE;
		CRT_DT = cRT_DT;
	}

	public ClIdImg(int iMG_SEQ, String iMG_TRDNO, String uSR_CDE, String iMG_PATH_LC, String iMG_TYPE, String cRT_DT) {
		super();
		IMG_SEQ = iMG_SEQ;
		IMG_TRDNO = iMG_TRDNO;
		USR_CDE = uSR_CDE;
		IMG_PATH_LC = iMG_PATH_LC;
		IMG_TYPE = iMG_TYPE;
		CRT_DT = cRT_DT;
	}

	@Override
	public String toString() {
		return "ClIdImg [IMG_SEQ=" + IMG_SEQ + ", IMG_TRDNO=" + IMG_TRDNO + ", USR_CDE=" + USR_CDE + ", IMG_PATH_LC="
				+ IMG_PATH_LC + ", IMG_TYPE=" + IMG_TYPE + ", IMG_FEATURE=" + IMG_FEATURE + ", CRT_DT=" + CRT_DT + "]";
	}

	
}
