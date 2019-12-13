package com.jeeplus.modules.app.api.penalty.request;

import java.io.Serializable;

/**
 * 违约金查询请求参数vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author saintlier
 * @date 2018-7-17
 */
public class ReqPenaltyQueryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗
	private String LOAN_NO;// 指定单笔保单号查询
	private String QUERY_DT;// 查询日期
	private String DN_CHANNEL;//渠道编码
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getQUERY_DT() {
		return QUERY_DT;
	}
	public void setQUERY_DT(String qUERY_DT) {
		QUERY_DT = qUERY_DT;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getLOAN_NO() {
		return LOAN_NO;
	}
	public void setLOAN_NO(String lOAN_NO) {
		LOAN_NO = lOAN_NO;
	}
	public String getDN_CHANNEL() {
		return DN_CHANNEL;
	}
	public void setDN_CHANNEL(String dN_CHANNEL) {
		DN_CHANNEL = dN_CHANNEL;
	}
	@Override
	public String toString() {
		return "ReqPenaltyQueryVO [serviceId=" + serviceId + ", LOAN_NO=" + LOAN_NO + ", QUERY_DT=" + QUERY_DT
				+ ", DN_CHANNEL=" + DN_CHANNEL + "]";
	}
	
	

}
