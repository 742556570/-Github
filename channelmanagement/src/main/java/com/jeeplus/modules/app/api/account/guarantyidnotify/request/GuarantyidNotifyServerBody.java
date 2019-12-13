package com.jeeplus.modules.app.api.account.guarantyidnotify.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 **@author 
 **@date 2018年7月2日
 **/
public class GuarantyidNotifyServerBody {

	// 保单号
		@XStreamAlias("GuarantyID")
		private String GuarantyID;
		
		// 贷款申请结果
		@XStreamAlias("ApproveResult")
		private String ApproveResult;
		
		// 信息描述
		@XStreamAlias("ApproveMsg")
		private String ApproveMsg;

		public String getGuarantyID() {
			return GuarantyID;
		}

		public void setGuarantyID(String guarantyID) {
			GuarantyID = guarantyID;
		}

		public String getApproveResult() {
			return ApproveResult;
		}

		public void setApproveResult(String approveResult) {
			ApproveResult = approveResult;
		}

		public String getApproveMsg() {
			return ApproveMsg;
		}

		public void setApproveMsg(String approveMsg) {
			ApproveMsg = approveMsg;
		}

		@Override
		public String toString() {
			return "GuarantyidNotifyServerRecord [GuarantyID=" + GuarantyID + ", ApproveResult=" + ApproveResult
					+ ", ApproveMsg=" + ApproveMsg + "]";
		}
		
		
	
	
}
