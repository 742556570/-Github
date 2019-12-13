package com.jeeplus.modules.app.api.account.header;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @description 请求公共报文头
 *
 */

public class ServerHeader {

	/**
	 * 唯一请求序列
	 */
	@XStreamAlias("SeqNo")
	private String SeqNo;

	/**
	 * 交易类型
	 */
	@XStreamAlias("ServiceType")
	private String ServiceType;

	/**
	 * 商户编号
	 */
	@XStreamAlias("ChannelType")
	private String ChannelType;

	/**
	 * 交易时间
	 */
	@XStreamAlias("ReqTime")
	private String ReqTime;

	public String getSeqNo() {
		return SeqNo;
	}

	public void setSeqNo(String seqNo) {
		SeqNo = seqNo;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public String getChannelType() {
		return ChannelType;
	}

	public void setChannelType(String channelType) {
		ChannelType = channelType;
	}

	public String getReqTime() {
		return ReqTime;
	}

	public void setReqTime(String reqTime) {
		ReqTime = reqTime;
	}

}
