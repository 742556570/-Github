package com.jeeplus.modules.app.api.account.loanrepaymentplanfromHS.request;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 核算还款计划
 * 
 * @author wangfz
 * @date 2019-01-02 
 */
@XmlRootElement
public class MsgBody {

	private String serviceId;
	private String channelId;
	private String guarantyId;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getGuarantyId() {
		return guarantyId;
	}

	public void setGuarantyId(String guarantyId) {
		this.guarantyId = guarantyId;
	}

	@Override
	public String toString() {
		return "RECORD [serviceId=" + serviceId + ", channelId=" + channelId + ", guarantyId=" + guarantyId + "]";
	}

	
}
