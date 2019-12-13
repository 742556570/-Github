package com.jeeplus.modules.app.api.eventreprot;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.elecpolicy.ElePolicyApi;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrLmtamt;

public class ReqJsonUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(ReqJsonUtils.class);

	public String getJsonStr(ClUsr usr, ClIdCardInfo idcard, ClCrdtExt crdExt, ClUsrLmtamt lmtAmt, String isapply) {

		String aid = usr.getUSR_CDE() ;
		
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		String prdCde = "";
		String channel = "";
		String token = "";
		String os_version = "";
		String app_version = "";

		String usrCde = "";
		String mobile = "";
		String id_no = "";
		String name = "";
		String event_time = "";
		String rc_status = "";
		String total_credit_limit = "0.00";
		String available_credit_limit = "0.00";

		String if_encash = "否";
		String if_people = "是";
		String if_set_psw = "否";

		String message_id = UUID.randomUUID().toString().replaceAll("-", "");

		if (usr != null) {
			token = usr.getUSR_TOKEN();
			if (StringUtils.isNotEmpty(usr.getPAY_PPWD())) {
				if_set_psw = "是";

			}
			usrCde = usr.getUSR_CDE();
			channel = "wdapp";
			mobile = usr.getUSR_TEL();
			if (usr.getUSR_OS() != null && usr.getUSR_OSVER() != null) {
				if (usr.getUSR_OSVER().equals("")) {
					usr.setUSR_OSVER("*");
				}
				os_version = usr.getUSR_OS() + "_" + usr.getUSR_OSVER();
			}
			if (usr.getUSR_CLNTVER() != null) {
				app_version = usr.getUSR_CLNTVER();
			}
		}

		if (idcard != null) {
			id_no = idcard.getID_NO();
			name = idcard.getCUST_NAME();
		}

		if (crdExt != null) {
			rc_status = crdExt.getSTATUS();

			total_credit_limit = crdExt.getADJ_AMT() == null ? "0.00" : df.format(crdExt.getADJ_AMT()).toString();
			
			
			event_time = DateUtils.getNowFullFmt();
			if (rc_status.equals("11")) {
				prdCde = crdExt.getPRD_CDE().toLowerCase();
			}
		}

		if (lmtAmt != null) {
			available_credit_limit = df.format(lmtAmt.getCreditAmount().subtract(lmtAmt.getUsedAmount())
					.add(lmtAmt.getRepayAmount())).toString();
		}

		if (isapply != null) {
			if_encash = isapply;
		}

		String json = "{" + "\"app_key\": \"wd\"," + "\"pro_type\": \"xjd\"," + "\"prd_code\": \"" + "tqd" + "\","
				+ "\"channel\": \"" + channel + "\"," + "\"token\": \"" + token + "\","
				+ "\"source_type\": \"prd-data\"," + "\"session_id\": \"\"," + "\"message_id\": \""
				+ message_id + "\"," + "\"user_id\": \"" + usrCde + "\"," + "\"mobile\": \"" + mobile + "\","
				+ "\"name\": \"" + name + "\"," + "\"id_no\": \"" + id_no + "\"," + "\"app_version\": \"" + app_version
				+ "\"," + "\"os_version\": \"" + os_version + "\"," + "\"sdk_version\": \"\"," + "\"event_time\": \""
				+ event_time + "\"," + "\"event\": \"rc_result\"," + "\"body\": {" + "\"rc_status\": \""
				+ getDescSt(rc_status) + "\"," + "\"total_credit_limit\": \"" + total_credit_limit + "\","
				+ "\"available_credit_limit\": \"" + available_credit_limit + "\"," + "\"if_encash\": \"" + if_encash
				+ "\"," + "\"if_people\": \"" + if_people + "\"," + "\"if_set_psw\": \"" + if_set_psw + "\"," + "\"props\": {}}"
				+ "}";

		logger.info(aid+":授信事件上报:"+json);
		return json;
	}

	private String getDescSt(String rc_status) {
		if (rc_status.equals("11")) {
			return "审核通过";
		} else if (rc_status.equals("10")) {
			return "审核被拒";
		} else if (rc_status.equals("01")) {
			return "审核中";
		} else if (rc_status.equals("20")) {
			return "转人工";
		} else {
			return "审核中";
		}
	}
}
