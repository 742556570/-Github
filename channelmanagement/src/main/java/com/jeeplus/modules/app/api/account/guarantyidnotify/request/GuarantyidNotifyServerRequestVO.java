package com.jeeplus.modules.app.api.account.guarantyidnotify.request;

import com.jeeplus.modules.app.api.account.header.ServerHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 ** @author
 ** @date 2018年7月2日
 **/
@XStreamAlias("SoapService")
public class GuarantyidNotifyServerRequestVO {

	@XStreamAlias("ServiceHeader")
	private ServerHeader serverHeader;
	@XStreamAlias("ServiceBody")
	private GuarantyidNotifyServerBody guarantyidNotifyServerBody;

	public ServerHeader getServerHeader() {
		return serverHeader;
	}

	public void setServerHeader(ServerHeader serverHeader) {
		this.serverHeader = serverHeader;
	}

	public GuarantyidNotifyServerBody getGuarantyidNotifyServerBody() {
		return guarantyidNotifyServerBody;
	}

	public void setGuarantyidNotifyServerBody(GuarantyidNotifyServerBody guarantyidNotifyServerBody) {
		this.guarantyidNotifyServerBody = guarantyidNotifyServerBody;
	}
}
