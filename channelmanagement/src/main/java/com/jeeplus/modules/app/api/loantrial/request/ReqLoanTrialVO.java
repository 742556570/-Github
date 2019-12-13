package com.jeeplus.modules.app.api.loantrial.request;

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
public class ReqLoanTrialVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗
	private String ORIG_PRCP;// 发放金额，对借新还旧来说申请金额应大于原贷款的所有金额
	private String DUE_DAY; // 指定还款日期（每月N日），建议扣款日<=21
	private String LOAN_ACTV_DT;// 发放日期
	private String LOAN_TYP;// 贷款品种代码，核算品种与信贷品种对应关系：p_loan_typ_gl_map
	private String INT_START_DT; // 起息日,仅限发放日当日
	private String LOAN_INT_RATE;// 执行利率
	private String PAYM_FREQ_UNIT;// 还款间隔单位 M: 月 W:周 Q: 季 Y:年
	private String PAYM_FREQ_FREQ;// 还款间隔,一般填1
	private String LOAN_PAYM_TYP;// 还款方式类型：00 自由还款法（不确定还款日）01 等额本息03 等额本金04 按期结息05 利随本清07 弹性还款（分阶段）08
									// 气球贷对应还款方式中的P_LOAN_MTD.MTD_TYP
	private String INSTM_IND;// 一般等额本金、等额本息填Y 否则填N
	private String TNR;// 贷款期数
	private String CAL_TOT_INSTM;// 实际计算期数
	private String PS_DUE_DT;// 还款日期
	private String FEE_CDE;// 费用代码
	private String RECV_PAY_IND;// 收付标志 R: 收 P: 付
	private String FEE_TYP;// 费用类型,来自于P_FEE_TYP
							// 表，必须存在如下费用：01:账户管理费02：滞纳金03：提前还款手续费违约金04：提现费05：年费06：分期收取费07：普通手续费08: 违约金09:
							// 代收代扣费99：其它120: 商户贴息
	private String FEE_CALC_TYP;// 费用计算方式 PCT：按比例计算AMT 按固定金额 RATE: 按照利率计算
	private String FEE_PCT_BASE;// 计算基础 01-贷款金额02-剩余本金03-还款本金04-还款本息05-欠款金额
	private String BASE_AMT;// 基准金额，如果没有传入，则根据计算基础及费用计算方式计算费用金额
	private String CHRG_PCT;// 费率或者费用计算利率，按比例计算时为费用比例，按利率计算时为费用利率
	private String HOLD_FEE_IND;// 是否周期性收费Y 周期性费用N 直接收取
	private String HOLD_FEE_SETL_DT;// 如果选择“周期性收费”，必须填写，收费开始日期
	private String ACC_IND;// 费用摊销标示 默认是N
	List<ReqLoanTrialVO> LmDnShdMtdTList;// 封装list
	List<ReqLoanTrialVO> LmPmShdTList;// 封装list
	List<ReqLoanTrialVO> LmFeeTxTList;// 封装list

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId ;
	}

	public String getORIG_PRCP() {
		return ORIG_PRCP;
	}

	public void setORIG_PRCP(String oRIG_PRCP) {
		ORIG_PRCP = oRIG_PRCP;
	}

	public String getDUE_DAY() {
		return DUE_DAY;
	}

	public void setDUE_DAY(String dUE_DAY) {
		DUE_DAY = dUE_DAY;
	}

	public String getLOAN_ACTV_DT() {
		return LOAN_ACTV_DT;
	}

	public void setLOAN_ACTV_DT(String lOAN_ACTV_DT) {
		LOAN_ACTV_DT = lOAN_ACTV_DT;
	}

	public String getLOAN_TYP() {
		return LOAN_TYP;
	}

	public void setLOAN_TYP(String lOAN_TYP) {
		LOAN_TYP = lOAN_TYP;
	}

	public String getINT_START_DT() {
		return INT_START_DT;
	}

	public void setINT_START_DT(String iNT_START_DT) {
		INT_START_DT = iNT_START_DT;
	}

	public String getLOAN_INT_RATE() {
		return LOAN_INT_RATE;
	}

	public void setLOAN_INT_RATE(String lOAN_INT_RATE) {
		LOAN_INT_RATE = lOAN_INT_RATE;
	}

	public String getPAYM_FREQ_UNIT() {
		return PAYM_FREQ_UNIT;
	}

	public void setPAYM_FREQ_UNIT(String pAYM_FREQ_UNIT) {
		PAYM_FREQ_UNIT = pAYM_FREQ_UNIT;
	}

	public String getPAYM_FREQ_FREQ() {
		return PAYM_FREQ_FREQ;
	}

	public void setPAYM_FREQ_FREQ(String pAYM_FREQ_FREQ) {
		PAYM_FREQ_FREQ = pAYM_FREQ_FREQ;
	}

	public String getLOAN_PAYM_TYP() {
		return LOAN_PAYM_TYP;
	}

	public void setLOAN_PAYM_TYP(String lOAN_PAYM_TYP) {
		LOAN_PAYM_TYP = lOAN_PAYM_TYP;
	}

	public String getINSTM_IND() {
		return INSTM_IND;
	}

	public void setINSTM_IND(String iNSTM_IND) {
		INSTM_IND = iNSTM_IND;
	}

	public String getTNR() {
		return TNR;
	}

	public void setTNR(String tNR) {
		TNR = tNR;
	}

	public String getCAL_TOT_INSTM() {
		return CAL_TOT_INSTM;
	}

	public void setCAL_TOT_INSTM(String cAL_TOT_INSTM) {
		CAL_TOT_INSTM = cAL_TOT_INSTM;
	}

	public String getPS_DUE_DT() {
		return PS_DUE_DT;
	}

	public void setPS_DUE_DT(String pS_DUE_DT) {
		PS_DUE_DT = pS_DUE_DT;
	}

	public String getFEE_CDE() {
		return FEE_CDE;
	}

	public void setFEE_CDE(String fEE_CDE) {
		FEE_CDE = fEE_CDE;
	}

	public String getRECV_PAY_IND() {
		return RECV_PAY_IND;
	}

	public void setRECV_PAY_IND(String rECV_PAY_IND) {
		RECV_PAY_IND = rECV_PAY_IND;
	}

	public String getFEE_TYP() {
		return FEE_TYP;
	}

	public void setFEE_TYP(String fEE_TYP) {
		FEE_TYP = fEE_TYP;
	}

	public String getFEE_CALC_TYP() {
		return FEE_CALC_TYP;
	}

	public void setFEE_CALC_TYP(String fEE_CALC_TYP) {
		FEE_CALC_TYP = fEE_CALC_TYP;
	}

	public String getFEE_PCT_BASE() {
		return FEE_PCT_BASE;
	}

	public void setFEE_PCT_BASE(String fEE_PCT_BASE) {
		FEE_PCT_BASE = fEE_PCT_BASE;
	}

	public String getBASE_AMT() {
		return BASE_AMT;
	}

	public void setBASE_AMT(String bASE_AMT) {
		BASE_AMT = bASE_AMT;
	}

	public String getCHRG_PCT() {
		return CHRG_PCT;
	}

	public void setCHRG_PCT(String cHRG_PCT) {
		CHRG_PCT = cHRG_PCT;
	}

	public String getHOLD_FEE_IND() {
		return HOLD_FEE_IND;
	}

	public void setHOLD_FEE_IND(String hOLD_FEE_IND) {
		HOLD_FEE_IND = hOLD_FEE_IND;
	}

	public String getHOLD_FEE_SETL_DT() {
		return HOLD_FEE_SETL_DT;
	}

	public void setHOLD_FEE_SETL_DT(String hOLD_FEE_SETL_DT) {
		HOLD_FEE_SETL_DT = hOLD_FEE_SETL_DT;
	}

	public String getACC_IND() {
		return ACC_IND;
	}

	public void setACC_IND(String aCC_IND) {
		ACC_IND = aCC_IND;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ReqLoanTrialVO> getLmDnShdMtdTList() {
		return LmDnShdMtdTList;
	}

	public void setLmDnShdMtdTList(List<ReqLoanTrialVO> lmDnShdMtdTList) {
		LmDnShdMtdTList = lmDnShdMtdTList;
	}

	public List<ReqLoanTrialVO> getLmPmShdTList() {
		return LmPmShdTList;
	}

	public void setLmPmShdTList(List<ReqLoanTrialVO> lmPmShdTList) {
		LmPmShdTList = lmPmShdTList;
	}

	public List<ReqLoanTrialVO> getLmFeeTxTList() {
		return LmFeeTxTList;
	}

	public void setLmFeeTxTList(List<ReqLoanTrialVO> lmFeeTxTList) {
		LmFeeTxTList = lmFeeTxTList;
	}

	@Override
	public String toString() {
		return "ReqLoanTrialVO [serviceId=" + serviceId + ", ORIG_PRCP=" + ORIG_PRCP + ", DUE_DAY=" + DUE_DAY
				+ ", LOAN_ACTV_DT=" + LOAN_ACTV_DT + ", LOAN_TYP=" + LOAN_TYP + ", INT_START_DT=" + INT_START_DT
				+ ", LOAN_INT_RATE=" + LOAN_INT_RATE + ", PAYM_FREQ_UNIT=" + PAYM_FREQ_UNIT + ", PAYM_FREQ_FREQ="
				+ PAYM_FREQ_FREQ + ", LOAN_PAYM_TYP=" + LOAN_PAYM_TYP + ", INSTM_IND=" + INSTM_IND + ", TNR=" + TNR
				+ ", CAL_TOT_INSTM=" + CAL_TOT_INSTM + ", PS_DUE_DT=" + PS_DUE_DT + ", FEE_CDE=" + FEE_CDE
				+ ", RECV_PAY_IND=" + RECV_PAY_IND + ", FEE_TYP=" + FEE_TYP + ", FEE_CALC_TYP=" + FEE_CALC_TYP
				+ ", FEE_PCT_BASE=" + FEE_PCT_BASE + ", BASE_AMT=" + BASE_AMT + ", CHRG_PCT=" + CHRG_PCT
				+ ", HOLD_FEE_IND=" + HOLD_FEE_IND + ", HOLD_FEE_SETL_DT=" + HOLD_FEE_SETL_DT + ", ACC_IND=" + ACC_IND
				+ ", LmDnShdMtdTList=" + LmDnShdMtdTList + ", LmPmShdTList=" + LmPmShdTList + ", LmFeeTxTList="
				+ LmFeeTxTList + "]";
	}

}
