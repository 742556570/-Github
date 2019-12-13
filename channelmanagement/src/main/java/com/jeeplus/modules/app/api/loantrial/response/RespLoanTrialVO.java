package com.jeeplus.modules.app.api.loantrial.response;

import java.io.Serializable;
import java.util.List;

/**
 * 贷款申请试算返回vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class RespLoanTrialVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode; // 返回码
	private String errorMsg; // 返回码信息
	private String TOTAL_NORM_INT; // 总利息金额
	private String TOTAL_AMT; // 总还款额
	private String TOTAL_FIRST_FEE; // 首期服务费总额
	private String TOTAL_FEE_AMT; // 费用总额
	private String TOTAL_SER_FEE; // 期缴服务费总额
	private String HOLD_FEE_SETL_DT; // 费用还款日期
	private String HOLD_FEE_IND; // 费用是否摊销
	private String FEE_TYP; // 费用类型
	private String FEE_AMT; // 费用金额
	private String FEE_CDE; // 费用代码
	private String PS_FEE_AMT_FIRST; // 首期服务费
	private String PERD_NO; // 期号
	private String PRCP_AMT; // 应归还本金
	private String INSTM_AMT; // 应归还期供
	private String NORM_INT; // 应归还利息
	private String INT_RATE; // 利率
	private String OD_INT_RATE; // 罚息利率
	private String DUE_DT; // 到期日
	private String PS_REM_PRCP; // 剩余本金
	private String PS_FEE_AMT; // 应还费用
	private String FREE_INTEREST; // 折扣的利息
	private String PS_FEE_AMT_SER; // 期缴管理费
	List<RespLoanTrialVO> PayFeeList;// 封装list
	List<RespLoanTrialVO> PayShdTryListAll;// 封装list

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTOTAL_NORM_INT() {
		return TOTAL_NORM_INT;
	}

	public void setTOTAL_NORM_INT(String tOTAL_NORM_INT) {
		TOTAL_NORM_INT = tOTAL_NORM_INT;
	}

	public String getTOTAL_AMT() {
		return TOTAL_AMT;
	}

	public void setTOTAL_AMT(String tOTAL_AMT) {
		TOTAL_AMT = tOTAL_AMT;
	}

	public String getTOTAL_FIRST_FEE() {
		return TOTAL_FIRST_FEE;
	}

	public void setTOTAL_FIRST_FEE(String tOTAL_FIRST_FEE) {
		TOTAL_FIRST_FEE = tOTAL_FIRST_FEE;
	}

	public String getTOTAL_FEE_AMT() {
		return TOTAL_FEE_AMT;
	}

	public void setTOTAL_FEE_AMT(String tOTAL_FEE_AMT) {
		TOTAL_FEE_AMT = tOTAL_FEE_AMT;
	}

	public String getTOTAL_SER_FEE() {
		return TOTAL_SER_FEE;
	}

	public void setTOTAL_SER_FEE(String tOTAL_SER_FEE) {
		TOTAL_SER_FEE = tOTAL_SER_FEE;
	}

	public String getHOLD_FEE_SETL_DT() {
		return HOLD_FEE_SETL_DT;
	}

	public void setHOLD_FEE_SETL_DT(String hOLD_FEE_SETL_DT) {
		HOLD_FEE_SETL_DT = hOLD_FEE_SETL_DT;
	}

	public String getHOLD_FEE_IND() {
		return HOLD_FEE_IND;
	}

	public void setHOLD_FEE_IND(String hOLD_FEE_IND) {
		HOLD_FEE_IND = hOLD_FEE_IND;
	}

	public String getFEE_TYP() {
		return FEE_TYP;
	}

	public void setFEE_TYP(String fEE_TYP) {
		FEE_TYP = fEE_TYP;
	}

	public String getFEE_AMT() {
		return FEE_AMT;
	}

	public void setFEE_AMT(String fEE_AMT) {
		FEE_AMT = fEE_AMT;
	}

	public String getFEE_CDE() {
		return FEE_CDE;
	}

	public void setFEE_CDE(String fEE_CDE) {
		FEE_CDE = fEE_CDE;
	}

	public String getPS_FEE_AMT_FIRST() {
		return PS_FEE_AMT_FIRST;
	}

	public void setPS_FEE_AMT_FIRST(String pS_FEE_AMT_FIRST) {
		PS_FEE_AMT_FIRST = pS_FEE_AMT_FIRST;
	}

	public String getPS_FEE_AMT_SER() {
		return PS_FEE_AMT_SER;
	}

	public void setPS_FEE_AMT_SER(String pS_FEE_AMT_SER) {
		PS_FEE_AMT_SER = pS_FEE_AMT_SER;
	}

	public List<RespLoanTrialVO> getPayFeeList() {
		return PayFeeList;
	}

	public void setPayFeeList(List<RespLoanTrialVO> payFeeList) {
		PayFeeList = payFeeList;
	}

	public String getPERD_NO() {
		return PERD_NO;
	}

	public void setPERD_NO(String pERD_NO) {
		PERD_NO = pERD_NO;
	}

	public String getPRCP_AMT() {
		return PRCP_AMT;
	}

	public void setPRCP_AMT(String pRCP_AMT) {
		PRCP_AMT = pRCP_AMT;
	}

	public String getINSTM_AMT() {
		return INSTM_AMT;
	}

	public void setINSTM_AMT(String iNSTM_AMT) {
		INSTM_AMT = iNSTM_AMT;
	}

	public String getNORM_INT() {
		return NORM_INT;
	}

	public void setNORM_INT(String nORM_INT) {
		NORM_INT = nORM_INT;
	}

	public String getINT_RATE() {
		return INT_RATE;
	}

	public void setINT_RATE(String iNT_RATE) {
		INT_RATE = iNT_RATE;
	}

	public String getOD_INT_RATE() {
		return OD_INT_RATE;
	}

	public void setOD_INT_RATE(String oD_INT_RATE) {
		OD_INT_RATE = oD_INT_RATE;
	}

	public String getDUE_DT() {
		return DUE_DT;
	}

	public void setDUE_DT(String dUE_DT) {
		DUE_DT = dUE_DT;
	}

	public String getPS_REM_PRCP() {
		return PS_REM_PRCP;
	}

	public void setPS_REM_PRCP(String pS_REM_PRCP) {
		PS_REM_PRCP = pS_REM_PRCP;
	}

	public String getPS_FEE_AMT() {
		return PS_FEE_AMT;
	}

	public void setPS_FEE_AMT(String pS_FEE_AMT) {
		PS_FEE_AMT = pS_FEE_AMT;
	}

	public String getFREE_INTEREST() {
		return FREE_INTEREST;
	}

	public void setFREE_INTEREST(String fREE_INTEREST) {
		FREE_INTEREST = fREE_INTEREST;
	}

	public List<RespLoanTrialVO> getPayShdTryListAll() {
		return PayShdTryListAll;
	}

	public void setPayShdTryListAll(List<RespLoanTrialVO> payShdTryListAll) {
		PayShdTryListAll = payShdTryListAll;
	}


	@Override
	public String toString() {
		return "RespLoanTrialVO [errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", TOTAL_NORM_INT="
				+ TOTAL_NORM_INT + ", TOTAL_AMT=" + TOTAL_AMT + ", TOTAL_FIRST_FEE=" + TOTAL_FIRST_FEE
				+ ", TOTAL_FEE_AMT=" + TOTAL_FEE_AMT + ", TOTAL_SER_FEE=" + TOTAL_SER_FEE + ", HOLD_FEE_SETL_DT="
				+ HOLD_FEE_SETL_DT + ", HOLD_FEE_IND=" + HOLD_FEE_IND + ", FEE_TYP=" + FEE_TYP + ", FEE_AMT=" + FEE_AMT
				+ ", FEE_CDE=" + FEE_CDE + ", PS_FEE_AMT_FIRST=" + PS_FEE_AMT_FIRST + ", PERD_NO=" + PERD_NO
				+ ", PRCP_AMT=" + PRCP_AMT + ", INSTM_AMT=" + INSTM_AMT + ", NORM_INT=" + NORM_INT + ", INT_RATE="
				+ INT_RATE + ", OD_INT_RATE=" + OD_INT_RATE + ", DUE_DT=" + DUE_DT + ", PS_REM_PRCP=" + PS_REM_PRCP
				+ ", PS_FEE_AMT=" + PS_FEE_AMT + ", FREE_INTEREST=" + FREE_INTEREST + ", PS_FEE_AMT_SER="
				+ PS_FEE_AMT_SER + ", PayFeeList=" + PayFeeList + ", PayShdTryListAll=" + PayShdTryListAll + "]";
	}

}
