package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;

public class ClUsr extends DataEntity<ClUsr> {

	private String USR_CDE; // 用户唯一标识
	private String USR_TEL; // 手机号码
	private String USR_STS; // 用户状态
	private String USR_LAST_DT; // 最近一次登陆时间
	private String USR_LAST_IP; // 最近一次登陆ip
	private String CRT_DT; // 用户创建时间
	private String USR_PLTFM; // 用户平台
	private String USR_OS; // 用户系统
	private String USR_OSVER; // 用户系统版本
	private String USR_CLNTVER; // 用户客户端版本
	private String USR_IMEI; // 用户设备标识
	private String USR_OPENID; // 用户芝麻授权openid
	private String USR_PROMOV; // 推送服务id
	private String USR_THRDID1; // 其他三方id1
	private String USR_THRDID2; // 其他三方id2
	private String USR_THRDID3; // 其他三方id3
	private String USR_TOKEN; // 用户最近登陆token
	private String USR_SOURCE; // 用户用户来源
	private String USR_GRD ; // 用户等级
	private String USR_PPWD; // 用户密码
	private String PAY_PPWD;//  支付密码
	private String APP_CHANNEL;

	public String getUSR_CDE() {
		return USR_CDE;
	}

	public void setUSR_CDE(String uSR_CDE) {
		if(null == uSR_CDE) {
			uSR_CDE = "";
		}
		USR_CDE = uSR_CDE;
	}

	public String getUSR_TEL() {
		return USR_TEL;
	}

	public void setUSR_TEL(String uSR_TEL) {
		if(null == uSR_TEL) {
			uSR_TEL = "";
		}
		USR_TEL = uSR_TEL;
	}

	public String getUSR_STS() {
		return USR_STS;
	}

	public void setUSR_STS(String uSR_STS) {
		if(null == uSR_STS) {
			uSR_STS = "";
		}
		USR_STS = uSR_STS;
	}

	public String getUSR_LAST_DT() {
		return USR_LAST_DT;
	}

	public void setUSR_LAST_DT(String uSR_LAST_DT) {
		if(null == uSR_LAST_DT) {
			uSR_LAST_DT = DateUtils.getNowFullFmt();
		}
		USR_LAST_DT = uSR_LAST_DT;
	}

	public String getUSR_LAST_IP() {
		return USR_LAST_IP;
	}

	public void setUSR_LAST_IP(String uSR_LAST_IP) {
		if(null == uSR_LAST_IP) {
			uSR_LAST_IP = "";
		}
		USR_LAST_IP = uSR_LAST_IP;
	}

	public String getCRT_DT() {
		return CRT_DT;
	}

	public void setCRT_DT(String cRT_DT) {
		if(null == cRT_DT) {
			cRT_DT = DateUtils.getNowFullFmt();
		}
		CRT_DT = cRT_DT;
	}

	public String getUSR_PLTFM() {
		return USR_PLTFM;
	}

	public void setUSR_PLTFM(String uSR_PLTFM) {
		if(null == uSR_PLTFM) {
			uSR_PLTFM = "";
		}
		USR_PLTFM = uSR_PLTFM;
	}

	public String getUSR_OS() {
		return USR_OS;
	}

	public void setUSR_OS(String uSR_OS) {
		if(null == uSR_OS) {
			uSR_OS = "";
		}
		USR_OS = uSR_OS;
	}

	public String getUSR_OSVER() {
		return USR_OSVER;
	}

	public void setUSR_OSVER(String uSR_OSVER) {
		if(null == uSR_OSVER) {
			uSR_OSVER = "";
		}
		USR_OSVER = uSR_OSVER;
	}

	public String getUSR_CLNTVER() {
		return USR_CLNTVER;
	}

	public void setUSR_CLNTVER(String uSR_CLNTVER) {
		if(null == uSR_CLNTVER) {
			uSR_CLNTVER = "";
		}
		USR_CLNTVER = uSR_CLNTVER;
	}

	public String getUSR_IMEI() {
		return USR_IMEI;
	}

	public void setUSR_IMEI(String uSR_IMEI) {
		if(null == uSR_IMEI) {
			uSR_IMEI = "";
		}
		USR_IMEI = uSR_IMEI;
	}

	public String getUSR_OPENID() {
		return USR_OPENID;
	}

	public void setUSR_OPENID(String uSR_OPENID) {
		if(null == uSR_OPENID) {
			uSR_OPENID = "";
		}
		USR_OPENID = uSR_OPENID;
	}

	public String getUSR_PROMOV() {
		return USR_PROMOV;
	}

	public void setUSR_PROMOV(String uSR_PROMOV) {
		if(null == uSR_PROMOV) {
			uSR_PROMOV = "";
		}
		USR_PROMOV = uSR_PROMOV;
	}

	public String getUSR_THRDID1() {
		return USR_THRDID1;
	}

	public void setUSR_THRDID1(String uSR_THRDID1) {
		if(null == uSR_THRDID1) {
			uSR_THRDID1 = "";
		}
		USR_THRDID1 = uSR_THRDID1;
	}

	public String getUSR_THRDID2() {
		return USR_THRDID2;
	}

	public void setUSR_THRDID2(String uSR_THRDID2) {
		if(null == uSR_THRDID2) {
			uSR_THRDID2 = "";
		}
		USR_THRDID2 = uSR_THRDID2;
	}

	public String getUSR_THRDID3() {
		return USR_THRDID3;
	}

	public void setUSR_THRDID3(String uSR_THRDID3) {
		if(null == uSR_THRDID3) {
			uSR_THRDID3 = "";
		}
		USR_THRDID3 = uSR_THRDID3;
	}

	public String getUSR_TOKEN() {
		return USR_TOKEN;
	}

	public void setUSR_TOKEN(String uSR_TOKEN) {
		if(null == uSR_TOKEN) {
			uSR_TOKEN = "";
		}
		USR_TOKEN = uSR_TOKEN;
	}

	public String getUSR_SOURCE() {
		return USR_SOURCE;
	}

	public void setUSR_SOURCE(String uSR_SOURCE) {
		if(null == uSR_SOURCE) {
			uSR_SOURCE = "";
		}
		USR_SOURCE = uSR_SOURCE;
	}

	public String getUSR_GRD() {
		if(StringUtils.isEmpty(USR_GRD)) {
			USR_GRD="unkown";
		}
		return USR_GRD;
	}

	public void setUSR_GRD(String uSR_GRD) {
		if(StringUtils.isEmpty(uSR_GRD)) {
			uSR_GRD="unkown";
		}
		USR_GRD = uSR_GRD;
	}

	public String getUSR_PPWD() {
		return USR_PPWD;
	}

	public void setUSR_PPWD(String uSR_PPWD) {
		if(null == uSR_PPWD) {
			uSR_PPWD = "";
		}
		USR_PPWD = uSR_PPWD;
	}

	
	public String getPAY_PPWD() {
		return PAY_PPWD;
	}

	public void setPAY_PPWD(String pAY_PPWD) {
		if(null == pAY_PPWD) {
			pAY_PPWD = "";
		}
		PAY_PPWD = pAY_PPWD;
	}

	public String getAPP_CHANNEL() {
		if(null == APP_CHANNEL) {
			APP_CHANNEL = "wdapp";
		}
		return APP_CHANNEL;
	}

	public void setAPP_CHANNEL(String aPP_CHANNEL) {
		APP_CHANNEL = aPP_CHANNEL;
	}

	public ClUsr() {
		super();
	}

	public ClUsr(String uSR_CDE, String uSR_TEL, String uSR_STS, String uSR_LAST_DT, String uSR_LAST_IP, String cRT_DT,
			String uSR_PLTFM, String uSR_OS, String uSR_OSVER, String uSR_CLNTVER, String uSR_IMEI, String uSR_OPENID,
			String uSR_PROMOV, String uSR_THRDID1, String uSR_THRDID2, String uSR_THRDID3, String uSR_TOKEN,
			String uSR_SOURCE, String uSR_GRD, String uSR_PPWD,String pAY_PPWD) {
		super();
		USR_CDE = uSR_CDE;
		USR_TEL = uSR_TEL;
		USR_STS = uSR_STS;
		USR_LAST_DT = uSR_LAST_DT;
		USR_LAST_IP = uSR_LAST_IP;
		CRT_DT = cRT_DT;
		USR_PLTFM = uSR_PLTFM;
		USR_OS = uSR_OS;
		USR_OSVER = uSR_OSVER;
		USR_CLNTVER = uSR_CLNTVER;
		USR_IMEI = uSR_IMEI;
		USR_OPENID = uSR_OPENID;
		USR_PROMOV = uSR_PROMOV;
		USR_THRDID1 = uSR_THRDID1;
		USR_THRDID2 = uSR_THRDID2;
		USR_THRDID3 = uSR_THRDID3;
		USR_TOKEN = uSR_TOKEN;
		USR_SOURCE = uSR_SOURCE;
		USR_GRD = uSR_GRD;
		USR_PPWD = uSR_PPWD;
		PAY_PPWD = pAY_PPWD;
	}

	@Override
	public String toString() {
		return "CL_USR [USR_CDE=" + USR_CDE + ", USR_TEL=" + USR_TEL + ", USR_STS=" + USR_STS + ", USR_LAST_DT="
				+ USR_LAST_DT + ", USR_LAST_IP=" + USR_LAST_IP + ", CRT_DT=" + CRT_DT + ", USR_PLTFM=" + USR_PLTFM
				+ ", USR_OS=" + USR_OS + ", USR_OSVER=" + USR_OSVER + ", USR_CLNTVER=" + USR_CLNTVER + ", USR_IMEI="
				+ USR_IMEI + ", USR_OPENID=" + USR_OPENID + ", USR_PROMOV=" + USR_PROMOV + ", USR_THRDID1="
				+ USR_THRDID1 + ", USR_THRDID2=" + USR_THRDID2 + ", USR_THRDID3=" + USR_THRDID3 + ", USR_TOKEN="
				+ USR_TOKEN + ", USR_SOURCE=" + USR_SOURCE + ", USR_GRD=" + USR_GRD + ", USR_PPWD=" + USR_PPWD + ", PAY_PPWD=" + PAY_PPWD +  "]";
	}

}
