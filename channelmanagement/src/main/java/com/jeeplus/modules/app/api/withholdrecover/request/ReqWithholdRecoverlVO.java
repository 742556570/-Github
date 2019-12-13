package com.jeeplus.modules.app.api.withholdrecover.request;

import java.io.Serializable;

/**
 * 代扣追偿请求参数vo
 * 
 * @param serviceId
 *            固定的不需要传
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqWithholdRecoverlVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗
	private String BEL_TYPE;// 业务类型 01信贷 02车贷 03房贷 07 追偿
	private String QUERY_DT;// 查询日期
	private String QUERY_TYPE;//查询业务类别
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getBEL_TYPE() {
		return BEL_TYPE;
	}
	public void setBEL_TYPE(String bEL_TYPE) {
		BEL_TYPE = bEL_TYPE;
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
	
	public String getQUERY_TYPE() {
		return QUERY_TYPE;
	}
	public void setQUERY_TYPE(String qUERY_TYPE) {
		QUERY_TYPE = qUERY_TYPE;
	}
	@Override
	public String toString() {
		return "ReqWithholdRecoverlVO [serviceId=" + serviceId + ", BEL_TYPE=" + BEL_TYPE + ", QUERY_DT=" + QUERY_DT
				+ ", QUERY_TYPE=" + QUERY_TYPE + "]";
	}

}
