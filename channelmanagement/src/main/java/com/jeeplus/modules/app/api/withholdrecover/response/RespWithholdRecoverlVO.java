package com.jeeplus.modules.app.api.withholdrecover.response;

import java.io.Serializable;
import java.util.List;

/**
 * 代扣追偿返回参数vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author wangfz
 * @date 2018-3-5
 */
public class RespWithholdRecoverlVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode; // 成功标识
	private String errorMessage;//错误原因
	private String policeCount;// 总条数
	private String policyNo;// 保单号
	private String bankCode;// 渠道
	private String checkDate;// 追偿日期
	private String planFee;// 追偿总金额
	private String capital;// 冲抵本金
	private String intamt;// 冲抵利息
	private String premium;//冲抵保费
	private String penalsum;// 响应时间
	private String sendTime;// 冲抵违约金
	private String setlInd;// 是否结清标识
	private List<RespWithholdRecoverlVO> policeList;// 返回list
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getPoliceCount() {
		return policeCount;
	}
	public void setPoliceCount(String policeCount) {
		this.policeCount = policeCount;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getPlanFee() {
		return planFee;
	}
	public void setPlanFee(String planFee) {
		this.planFee = planFee;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getIntamt() {
		return intamt;
	}
	public void setIntamt(String intamt) {
		this.intamt = intamt;
	}
	public String getPremium() {
		return premium;
	}
	public void setPremium(String premium) {
		this.premium = premium;
	}
	public String getPenalsum() {
		return penalsum;
	}
	public void setPenalsum(String penalsum) {
		this.penalsum = penalsum;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public List<RespWithholdRecoverlVO> getPoliceList() {
		return policeList;
	}
	public void setPoliceList(List<RespWithholdRecoverlVO> policeList) {
		this.policeList = policeList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getSetlInd() {
		return setlInd;
	}
	public void setSetlInd(String setlInd) {
		this.setlInd = setlInd;
	}
	@Override
	public String toString() {
		return "RespWithholdRecoverlVO [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", policeCount="
				+ policeCount + ", policyNo=" + policyNo + ", bankCode=" + bankCode + ", checkDate=" + checkDate
				+ ", planFee=" + planFee + ", capital=" + capital + ", intamt=" + intamt + ", premium=" + premium
				+ ", penalsum=" + penalsum + ", sendTime=" + sendTime + ", setlInd=" + setlInd + ", policeList="
				+ policeList + "]";
	}	
	
}
