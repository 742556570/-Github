package com.jeeplus.modules.app.api.invalidpolicy.request;

import java.io.Serializable;

/**
 * 放联系人详细信息vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqLinkPersonDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String linkPhone; // 联系电话  默认为空，与综合保持一致
	private String linkShip; // 联系人与客户的关系 
	private String linkMobile; // 联系人手机号码 
	private String linkAdress;// 联系人地址 默认为空，与综合保持一致
	private String insuredFlag;// 被保人标志 默认1，与综合保持一致。
	private String linkName;// 借款人姓名
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkShip() {
		return linkShip;
	}
	public void setLinkShip(String linkShip) {
		this.linkShip = linkShip;
	}
	public String getLinkMobile() {
		return linkMobile;
	}
	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}
	public String getLinkAdress() {
		return linkAdress;
	}
	public void setLinkAdress(String linkAdress) {
		this.linkAdress = linkAdress;
	}
	public String getInsuredFlag() {
		return insuredFlag;
	}
	public void setInsuredFlag(String insuredFlag) {
		this.insuredFlag = insuredFlag;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ReqLinkPersonDetailVO [linkPhone=" + linkPhone + ", linkShip=" + linkShip + ", linkMobile=" + linkMobile
				+ ", linkAdress=" + linkAdress + ", insuredFlag=" + insuredFlag + ", linkName=" + linkName + "]";
	}

	
	
}
