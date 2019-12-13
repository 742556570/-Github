package com.jeeplus.modules.app.entity;

import com.jeeplus.common.persistence.DataEntity;

public class ClCapChannel  extends DataEntity<ClCapChannel> {
	private Integer CAP_SEQ; //主键自增
	private String CAP_CDE; //资金方代码
	private String CAP_INSTU_CDE; //发布金融机构
	private String CAP_INSTU_NAME; //被保险人名称银行中文名称
	private String CAP_INSUR_CDE; //放款银行统一信用代码
	private String KIND_CDE; //条款代码
	private String KIND_NAME; //条款名称
	private String SUM_AMOUNT; //总保险金额
	private String BUSINESS_NATURE; //业务来源
	private String SECURE_CDE; //险种id
	private String SECURE_NAME; //险种名称
	private String CHANNEL_CDE; //渠道代码
	private String CHANNEL_NAM; //渠道名称
	private String HANDLER1_CDE; //销售人员代码1
	private String OPERATOR_CDE; //录单人员代码
	private String OPERATE_SITE; //银行资金渠道
	private String COM_CED; //销售人员对应组的核心代码
	private String MAKE_COM; //受理机构,对应的核心代码
	private String CRT_DT; //登记日期
	private String CRT_USR; //登记人
	private String MDF_DT; //最后修改日期
	private String MDF_USR; //最后修改人
	private String BCH_CDE = "00370100";

	public Integer getCAP_SEQ() {
		return CAP_SEQ;
	}

	public void setCAP_SEQ(Integer cAP_SEQ) {
		CAP_SEQ = cAP_SEQ;
	}

	public String getCAP_CDE() {
		return CAP_CDE;
	}

	public void setCAP_CDE(String cAP_CDE) {
		CAP_CDE = cAP_CDE;
	}

	public String getCAP_INSTU_CDE() {
		return CAP_INSTU_CDE;
	}

	public void setCAP_INSTU_CDE(String cAP_INSTU_CDE) {
		CAP_INSTU_CDE = cAP_INSTU_CDE;
	}

	public String getCAP_INSTU_NAME() {
		return CAP_INSTU_NAME;
	}

	public void setCAP_INSTU_NAME(String cAP_INSTU_NAME) {
		CAP_INSTU_NAME = cAP_INSTU_NAME;
	}

	public String getCAP_INSUR_CDE() {
		return CAP_INSUR_CDE;
	}

	public void setCAP_INSUR_CDE(String cAP_INSUR_CDE) {
		CAP_INSUR_CDE = cAP_INSUR_CDE;
	}

	public String getKIND_CDE() {
		return KIND_CDE;
	}

	public void setKIND_CDE(String kIND_CDE) {
		KIND_CDE = kIND_CDE;
	}

	public String getKIND_NAME() {
		return KIND_NAME;
	}

	public void setKIND_NAME(String kIND_NAME) {
		KIND_NAME = kIND_NAME;
	}

	public String getSUM_AMOUNT() {
		return SUM_AMOUNT;
	}

	public void setSUM_AMOUNT(String sUM_AMOUNT) {
		SUM_AMOUNT = sUM_AMOUNT;
	}

	public String getBUSINESS_NATURE() {
		return BUSINESS_NATURE;
	}

	public void setBUSINESS_NATURE(String bUSINESS_NATURE) {
		BUSINESS_NATURE = bUSINESS_NATURE;
	}

	public String getSECURE_CDE() {
		return SECURE_CDE;
	}

	public void setSECURE_CDE(String sECURE_CDE) {
		SECURE_CDE = sECURE_CDE;
	}

	public String getSECURE_NAME() {
		return SECURE_NAME;
	}

	public void setSECURE_NAME(String sECURE_NAME) {
		SECURE_NAME = sECURE_NAME;
	}

	public String getCHANNEL_CDE() {
		return CHANNEL_CDE;
	}

	public void setCHANNEL_CDE(String cHANNEL_CDE) {
		CHANNEL_CDE = cHANNEL_CDE;
	}

	public String getCHANNEL_NAM() {
		return CHANNEL_NAM;
	}

	public void setCHANNEL_NAM(String cHANNEL_NAM) {
		CHANNEL_NAM = cHANNEL_NAM;
	}

	public String getHANDLER1_CDE() {
		return HANDLER1_CDE;
	}

	public void setHANDLER1_CDE(String hANDLER1_CDE) {
		HANDLER1_CDE = hANDLER1_CDE;
	}

	public String getOPERATOR_CDE() {
		return OPERATOR_CDE;
	}

	public void setOPERATOR_CDE(String oPERATOR_CDE) {
		OPERATOR_CDE = oPERATOR_CDE;
	}

	public String getOPERATE_SITE() {
		return OPERATE_SITE;
	}

	public void setOPERATE_SITE(String oPERATE_SITE) {
		OPERATE_SITE = oPERATE_SITE;
	}

	public String getCOM_CED() {
		return COM_CED;
	}

	public void setCOM_CED(String cOM_CED) {
		COM_CED = cOM_CED;
	}

	public String getMAKE_COM() {
		return MAKE_COM;
	}

	public void setMAKE_COM(String mAKE_COM) {
		MAKE_COM = mAKE_COM;
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

	
	public String getBCH_CDE() {
		return BCH_CDE;
	}

	public void setBCH_CDE(String bCH_CDE) {
		BCH_CDE = bCH_CDE;
	}

	public ClCapChannel() {
		super();
	}

	public ClCapChannel(Integer cAP_SEQ, String cAP_CDE, String cAP_INSTU_CDE, String cAP_INSTU_NAME,
			String cAP_INSUR_CDE, String kIND_CDE, String kIND_NAME, String sUM_AMOUNT, String bUSINESS_NATURE,
			String sECURE_CDE, String sECURE_NAME, String cHANNEL_CDE, String cHANNEL_NAM, String hANDLER1_CDE,
			String oPERATOR_CDE, String oPERATE_SITE, String cOM_CED, String mAKE_COM, String cRT_DT, String cRT_USR,
			String mDF_DT, String mDF_USR) {
		super();
		CAP_SEQ = cAP_SEQ;
		CAP_CDE = cAP_CDE;
		CAP_INSTU_CDE = cAP_INSTU_CDE;
		CAP_INSTU_NAME = cAP_INSTU_NAME;
		CAP_INSUR_CDE = cAP_INSUR_CDE;
		KIND_CDE = kIND_CDE;
		KIND_NAME = kIND_NAME;
		SUM_AMOUNT = sUM_AMOUNT;
		BUSINESS_NATURE = bUSINESS_NATURE;
		SECURE_CDE = sECURE_CDE;
		SECURE_NAME = sECURE_NAME;
		CHANNEL_CDE = cHANNEL_CDE;
		CHANNEL_NAM = cHANNEL_NAM;
		HANDLER1_CDE = hANDLER1_CDE;
		OPERATOR_CDE = oPERATOR_CDE;
		OPERATE_SITE = oPERATE_SITE;
		COM_CED = cOM_CED;
		MAKE_COM = mAKE_COM;
		CRT_DT = cRT_DT;
		CRT_USR = cRT_USR;
		MDF_DT = mDF_DT;
		MDF_USR = mDF_USR;
	}

	@Override
	public String toString() {
		return "ClCapChannel [CAP_SEQ=" + CAP_SEQ + ", CAP_CDE=" + CAP_CDE + ", CAP_INSTU_CDE=" + CAP_INSTU_CDE
				+ ", CAP_INSTU_NAME=" + CAP_INSTU_NAME + ", CAP_INSUR_CDE=" + CAP_INSUR_CDE + ", KIND_CDE=" + KIND_CDE
				+ ", KIND_NAME=" + KIND_NAME + ", SUM_AMOUNT=" + SUM_AMOUNT + ", BUSINESS_NATURE=" + BUSINESS_NATURE
				+ ", SECURE_CDE=" + SECURE_CDE + ", SECURE_NAME=" + SECURE_NAME + ", CHANNEL_CDE=" + CHANNEL_CDE
				+ ", CHANNEL_NAM=" + CHANNEL_NAM + ", HANDLER1_CDE=" + HANDLER1_CDE + ", OPERATOR_CDE=" + OPERATOR_CDE
				+ ", OPERATE_SITE=" + OPERATE_SITE + ", COM_CED=" + COM_CED + ", MAKE_COM=" + MAKE_COM + ", CRT_DT="
				+ CRT_DT + ", CRT_USR=" + CRT_USR + ", MDF_DT=" + MDF_DT + ", MDF_USR=" + MDF_USR + "]";
	}

}
