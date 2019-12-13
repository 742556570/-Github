package com.jeeplus.modules.app.api.product.request;

import java.io.Serializable;
/**
 * 同步产品信息请求参数vo
 * @param serviceId 固定的不需要传
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqProductVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceId; // 交易吗 
	private String typ_cde; // 贷款品种代码
	private String acct_role_desc;// 核算操作类型
	private String opt_typ;// 新增 删除 修改

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTyp_cde() {
		return typ_cde;
	}

	public void setTyp_cde(String typ_cde) {
		this.typ_cde = typ_cde;
	}

	public String getAcct_role_desc() {
		return acct_role_desc;
	}

	public void setAcct_role_desc(String acct_role_desc) {
		this.acct_role_desc = acct_role_desc;
	}

	public String getOpt_typ() {
		return opt_typ;
	}

	public void setOpt_typ(String opt_typ) {
		this.opt_typ = opt_typ;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReqProductVO [serviceId=" + serviceId + ", typ_cde=" + typ_cde + ", acct_role_desc=" + acct_role_desc
				+ ", opt_typ=" + opt_typ + "]";
	}
}
