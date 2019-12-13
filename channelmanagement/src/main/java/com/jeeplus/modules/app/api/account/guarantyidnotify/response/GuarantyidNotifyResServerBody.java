package com.jeeplus.modules.app.api.account.guarantyidnotify.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
public class GuarantyidNotifyResServerBody {

		// 贷款申请结果
		@XStreamAlias("ResCode")
		private String ResCode;
		
		// 信息描述
		@XStreamAlias("ResMessage")
		private String ResMessage;

		public String getResCode() {
			return ResCode;
		}

		public void setResCode(String resCode) {
			ResCode = resCode;
		}

		public String getResMessage() {
			return ResMessage;
		}

		public void setResMessage(String resMessage) {
			ResMessage = resMessage;
		}

		
	
}
