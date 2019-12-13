package com.jeeplus.modules.app.api.penalty.response;

import java.io.Serializable;
import java.util.List;

/**
 * 违约金查询返回结果vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author saintlier
 * @date 2018-7-17
 */
public class RespPenaltyQueryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode; // 成功标识
	private String errorMessage;//错误原因
	private String sendTime;// 响应时间
	private String policeCount;// 总条数
	private String policyNo;// 保单号
	private String bankCode;// 渠道
	private String penalsum;// 提前还款违约金
	private String setl_dt;// 提前还款日期

	private List<RespPenaltyQueryVO> policeList;// 返回list
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSetl_dt() {
		return setl_dt;
	}
	public void setSetl_dt(String setl_dt) {
		this.setl_dt = setl_dt;
	}
	public List<RespPenaltyQueryVO> getPoliceList() {
		return policeList;
	}
	public void setPoliceList(List<RespPenaltyQueryVO> policeList) {
		this.policeList = policeList;
	}
	@Override
	public String toString() {
		return "RespPenaltyQueryVO [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", sendTime="
				+ sendTime + ", policeCount=" + policeCount + ", policyNo=" + policyNo + ", bankCode=" + bankCode
				+ ", penalsum=" + penalsum + ", setl_dt=" + setl_dt + ", policeList=" + policeList + "]";
	}
	
	
}
