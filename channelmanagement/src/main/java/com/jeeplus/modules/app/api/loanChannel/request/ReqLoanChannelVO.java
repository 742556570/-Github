package com.jeeplus.modules.app.api.loanChannel.request;

import java.io.Serializable;
import java.util.List;

/**
 * 放款渠道同步请求参数vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqLoanChannelVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗
	private String DN_CHANNEL; // 放款渠道
	private int TNR_OPT; // 期限设定
	private String LOAN_RATE_MODE;// 利率模式
	private double LOAN_BASE_RATE;// 基准利率
	private String INT_ADJ_PCT;// 利率浮动比例
	private String RATE_TYPE;// 浮动形式 U上浮D下浮
	private double INT_RAT;// 利率
	private double OD_INT_RATE;// 罚息利率
	private String LEND_CHA_NAM;// 放款渠道名称
	private String OPT_TYP;// 新增、修改、删除
	private String LAST_CHG_USR;// 最后更新用户
	private String LAST_CHG_DT;// 最后修改日期
	private String BEL_TYPE;// 业务类型 01信贷 02车贷 03房贷
	private String TOTTERM_X;//
	private String XQ_RATE;//
	List<ReqLoanChannelVO> PLoanTypMtdList;// 封装list

	public String getDN_CHANNEL() {
		return DN_CHANNEL;
	}

	public void setDN_CHANNEL(String dN_CHANNEL) {
		DN_CHANNEL = dN_CHANNEL;
	}

	public int getTNR_OPT() {
		return TNR_OPT;
	}

	public void setTNR_OPT(int tNR_OPT) {
		TNR_OPT = tNR_OPT;
	}

	public String getLOAN_RATE_MODE() {
		return LOAN_RATE_MODE;
	}

	public void setLOAN_RATE_MODE(String lOAN_RATE_MODE) {
		LOAN_RATE_MODE = lOAN_RATE_MODE;
	}

	public double getLOAN_BASE_RATE() {
		return LOAN_BASE_RATE;
	}

	public void setLOAN_BASE_RATE(double lOAN_BASE_RATE) {
		LOAN_BASE_RATE = lOAN_BASE_RATE;
	}

	public String getINT_ADJ_PCT() {
		return INT_ADJ_PCT;
	}

	public void setINT_ADJ_PCT(String iNT_ADJ_PCT) {
		INT_ADJ_PCT = iNT_ADJ_PCT;
	}

	public String getRATE_TYPE() {
		return RATE_TYPE;
	}

	public void setRATE_TYPE(String rATE_TYPE) {
		RATE_TYPE = rATE_TYPE;
	}

	public double getINT_RAT() {
		return INT_RAT;
	}

	public void setINT_RAT(double iNT_RAT) {
		INT_RAT = iNT_RAT;
	}

	public double getOD_INT_RATE() {
		return OD_INT_RATE;
	}

	public void setOD_INT_RATE(double oD_INT_RATE) {
		OD_INT_RATE = oD_INT_RATE;
	}

	public String getLEND_CHA_NAM() {
		return LEND_CHA_NAM;
	}

	public void setLEND_CHA_NAM(String lEND_CHA_NAM) {
		LEND_CHA_NAM = lEND_CHA_NAM;
	}

	public String getOPT_TYP() {
		return OPT_TYP;
	}

	public void setOPT_TYP(String oPT_TYP) {
		OPT_TYP = oPT_TYP;
	}

	public String getLAST_CHG_USR() {
		return LAST_CHG_USR;
	}

	public void setLAST_CHG_USR(String lAST_CHG_USR) {
		LAST_CHG_USR = lAST_CHG_USR;
	}

	public String getLAST_CHG_DT() {
		return LAST_CHG_DT;
	}

	public void setLAST_CHG_DT(String lAST_CHG_DT) {
		LAST_CHG_DT = lAST_CHG_DT;
	}

	public String getBEL_TYPE() {
		return BEL_TYPE;
	}

	public void setBEL_TYPE(String bEL_TYPE) {
		BEL_TYPE = bEL_TYPE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTOTTERM_X() {
		return TOTTERM_X;
	}

	public void setTOTTERM_X(String tOTTERM_X) {
		TOTTERM_X = tOTTERM_X;
	}

	public String getXQ_RATE() {
		return XQ_RATE;
	}

	public void setXQ_RATE(String xQ_RATE) {
		XQ_RATE = xQ_RATE;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<ReqLoanChannelVO> getPLoanTypMtdList() {
		return PLoanTypMtdList;
	}

	public void setPLoanTypMtdList(List<ReqLoanChannelVO> pLoanTypMtdList) {
		PLoanTypMtdList = pLoanTypMtdList;
	}

	@Override
	public String toString() {
		return "ReqLoanChannelVO [serviceId=" + serviceId + ", DN_CHANNEL=" + DN_CHANNEL + ", TNR_OPT=" + TNR_OPT
				+ ", LOAN_RATE_MODE=" + LOAN_RATE_MODE + ", LOAN_BASE_RATE=" + LOAN_BASE_RATE + ", INT_ADJ_PCT="
				+ INT_ADJ_PCT + ", RATE_TYPE=" + RATE_TYPE + ", INT_RAT=" + INT_RAT + ", OD_INT_RATE=" + OD_INT_RATE
				+ ", LEND_CHA_NAM=" + LEND_CHA_NAM + ", OPT_TYP=" + OPT_TYP + ", LAST_CHG_USR=" + LAST_CHG_USR
				+ ", LAST_CHG_DT=" + LAST_CHG_DT + ", BEL_TYPE=" + BEL_TYPE + ", TOTTERM_X=" + TOTTERM_X + ", XQ_RATE="
				+ XQ_RATE + ", PLoanTypMtdList=" + PLoanTypMtdList + "]";
	}
}
