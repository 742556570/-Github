package com.jeeplus.modules.app.api.account.guarantyidnotify.response;

import com.jeeplus.modules.app.api.account.header.ServerHeader;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 ** @author
 ** @date 2018年7月2日
 **/
@XStreamAlias("SoapService")
public class GuarantyidNotifyServerResponseVO {

	@XStreamAlias("ServiceHeader")
	private ServerHeader serverHeader;
	@XStreamAlias("ServiceBody")
	private GuarantyidNotifyResServerBody guarantyidNotifyResServerBody;

	public ServerHeader getServerHeader() {
		return serverHeader;
	}

	public GuarantyidNotifyResServerBody getGuarantyidNotifyResServerBody() {
		return guarantyidNotifyResServerBody;
	}

	public void setGuarantyidNotifyResServerBody(GuarantyidNotifyResServerBody guarantyidNotifyResServerBody) {
		this.guarantyidNotifyResServerBody = guarantyidNotifyResServerBody;
	}

	public void setServerHeader(ServerHeader serverHeader) {
		this.serverHeader = serverHeader;
	}
}
