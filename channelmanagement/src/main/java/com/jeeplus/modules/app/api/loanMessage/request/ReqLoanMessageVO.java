package com.jeeplus.modules.app.api.loanMessage.request;

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
public class ReqLoanMessageVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗
	private String LOAN_CONT_NO; // 申请号
	private String BEL_TYPE; // 业务类型 01信贷 02车贷 03房贷
	private String BCH_CDE;// 出账机构，分支机构代码，在S_BCH中存在(门店代码)
	private String LOAN_NO;// 保单号
	private String CUST_NAME;// 借款人姓名
	private String ID_NO;// 借款人证件号
	private String ORIG_PRCP;// 发放金额，对借新还旧来说申请金额应大于原贷款的所有金额
	private String LOAN_ACTV_DT;// 发放日期
	private String LOAN_TYP;// 贷款品种代码，核算品种与信贷品种对应关系：p_loan_typ_gl_map
	private String LOAN_PAYM_MTD;// 还款方式，对应P_LOAN_MTD.
									// MTD_CDE，该方式代码必须在还款方式配置表p_loan_mtd中已经配置，后台程序通过查询方式表构造每个借据的还款方式 M0001等额本息
									// M0002等额本金 SYS001利随本清
	private String LOAN_PAYM_TYP;// 还款方式类型：00 自由还款法（不确定还款日）01 等额本息 02 本息不同间隔 03 等额本金 04 按期结息 05 利随本清06
									// 本金递增递减（本金递增如每期递增100第一期还100第二期200一次类推）07 弹性还款（分阶段）08
									// 气球贷对应还款方式中的P_LOAN_MTD.MTD_TYP09 其他
	private String TNR;// 贷款期数
	private String DN_CHANNEL;// 放款渠道（对应银行）
	private String FEE_ACT;// 保费费率
	private String HOLD_FEE_IND;// 是否周期性收费Y 周期性费用(期交) N 直接收取(趸交)
	private String RISK_CODE;// 险种代码
	private String ACC_IND;// 费用摊销标识
	private String FIRST_FEE_RATE;// 首期服务费率
	private String SERV_FEE_RATE;// 期交服务费率
	private String GUAR_FEE_RATE;// 期交保费费率
	private String PH_FLAG;// 是否普惠数据
	List<ReqLoanMessageVO> lmFeeTxTList;// 封装list

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getLOAN_CONT_NO() {
		return LOAN_CONT_NO;
	}

	public void setLOAN_CONT_NO(String lOAN_CONT_NO) {
		LOAN_CONT_NO = lOAN_CONT_NO;
	}

	public String getBEL_TYPE() {
		return BEL_TYPE;
	}

	public void setBEL_TYPE(String bEL_TYPE) {
		BEL_TYPE = bEL_TYPE;
	}

	public String getBCH_CDE() {
		return BCH_CDE;
	}

	public void setBCH_CDE(String bCH_CDE) {
		BCH_CDE = bCH_CDE;
	}

	public String getLOAN_NO() {
		return LOAN_NO;
	}

	public void setLOAN_NO(String lOAN_NO) {
		LOAN_NO = lOAN_NO;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}

	public String getID_NO() {
		return ID_NO;
	}

	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}

	public String getORIG_PRCP() {
		return ORIG_PRCP;
	}

	public void setORIG_PRCP(String oRIG_PRCP) {
		ORIG_PRCP = oRIG_PRCP;
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

	public String getLOAN_PAYM_MTD() {
		return LOAN_PAYM_MTD;
	}

	public void setLOAN_PAYM_MTD(String lOAN_PAYM_MTD) {
		LOAN_PAYM_MTD = lOAN_PAYM_MTD;
	}

	public String getLOAN_PAYM_TYP() {
		return LOAN_PAYM_TYP;
	}

	public void setLOAN_PAYM_TYP(String lOAN_PAYM_TYP) {
		LOAN_PAYM_TYP = lOAN_PAYM_TYP;
	}

	public String getTNR() {
		return TNR;
	}

	public void setTNR(String tNR) {
		TNR = tNR;
	}

	public String getDN_CHANNEL() {
		return DN_CHANNEL;
	}

	public void setDN_CHANNEL(String dN_CHANNEL) {
		DN_CHANNEL = dN_CHANNEL;
	}

	public String getFEE_ACT() {
		return FEE_ACT;
	}

	public void setFEE_ACT(String fEE_ACT) {
		FEE_ACT = fEE_ACT;
	}

	public String getHOLD_FEE_IND() {
		return HOLD_FEE_IND;
	}

	public void setHOLD_FEE_IND(String hOLD_FEE_IND) {
		HOLD_FEE_IND = hOLD_FEE_IND;
	}

	public String getRISK_CODE() {
		return RISK_CODE;
	}

	public void setRISK_CODE(String rISK_CODE) {
		RISK_CODE = rISK_CODE;
	}

	public String getACC_IND() {
		return ACC_IND;
	}

	public void setACC_IND(String aCC_IND) {
		ACC_IND = aCC_IND;
	}

	public String getFIRST_FEE_RATE() {
		return FIRST_FEE_RATE;
	}

	public void setFIRST_FEE_RATE(String fIRST_FEE_RATE) {
		FIRST_FEE_RATE = fIRST_FEE_RATE;
	}

	public String getSERV_FEE_RATE() {
		return SERV_FEE_RATE;
	}

	public void setSERV_FEE_RATE(String sERV_FEE_RATE) {
		SERV_FEE_RATE = sERV_FEE_RATE;
	}

	public String getGUAR_FEE_RATE() {
		return GUAR_FEE_RATE;
	}

	public void setGUAR_FEE_RATE(String gUAR_FEE_RATE) {
		GUAR_FEE_RATE = gUAR_FEE_RATE;
	}

	public String getPH_FLAG() {
		return PH_FLAG;
	}

	public void setPH_FLAG(String pH_FLAG) {
		PH_FLAG = pH_FLAG;
	}

	public List<ReqLoanMessageVO> getLmFeeTxTList() {
		return lmFeeTxTList;
	}

	public void setLmFeeTxTList(List<ReqLoanMessageVO> lmFeeTxTList) {
		this.lmFeeTxTList = lmFeeTxTList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReqLoanMessageVO [serviceId=" + serviceId + ", LOAN_CONT_NO=" + LOAN_CONT_NO + ", BEL_TYPE=" + BEL_TYPE
				+ ", BCH_CDE=" + BCH_CDE + ", LOAN_NO=" + LOAN_NO + ", CUST_NAME=" + CUST_NAME + ", ID_NO=" + ID_NO
				+ ", ORIG_PRCP=" + ORIG_PRCP + ", LOAN_ACTV_DT=" + LOAN_ACTV_DT + ", LOAN_TYP=" + LOAN_TYP
				+ ", LOAN_PAYM_MTD=" + LOAN_PAYM_MTD + ", LOAN_PAYM_TYP=" + LOAN_PAYM_TYP + ", TNR=" + TNR
				+ ", DN_CHANNEL=" + DN_CHANNEL + ", FEE_ACT=" + FEE_ACT + ", HOLD_FEE_IND=" + HOLD_FEE_IND
				+ ", RISK_CODE=" + RISK_CODE + ", ACC_IND=" + ACC_IND + ", FIRST_FEE_RATE=" + FIRST_FEE_RATE
				+ ", SERV_FEE_RATE=" + SERV_FEE_RATE + ", GUAR_FEE_RATE=" + GUAR_FEE_RATE + ", PH_FLAG=" + PH_FLAG
				+ ", lmFeeTxTList=" + lmFeeTxTList + "]";
	}

}
