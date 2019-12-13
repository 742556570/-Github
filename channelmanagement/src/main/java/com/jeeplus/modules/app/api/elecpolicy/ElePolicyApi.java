package com.jeeplus.modules.app.api.elecpolicy;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.app.api.product.PropertiesUtil;
import com.jeeplus.modules.app.constant.AppNormalConstants;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClCapChannel;
import com.jeeplus.modules.app.entity.ClIdCardInfo;
import com.jeeplus.modules.app.entity.ClPrdInfo;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrApply;
import com.jeeplus.modules.app.entity.ClUsrInfo;
import com.jeeplus.modules.app.service.ClApplyService;
import com.jeeplus.modules.app.utils.HttpConUtils;

public class ElePolicyApi {
	
	private final static Logger logger = LoggerFactory.getLogger(ElePolicyApi.class);
	
	public boolean sendElePolicy(ClCapChannel capChannel,ClPrdInfo prd,ClUsr usr,ClIdCardInfo idcard,ClUsrApply apply,ClUsrInfo usrInfo,
			BigDecimal premium) {
		String aid = usr.getUSR_CDE() + "_" + apply.getPolicyNo();
		
		String url = PropertiesUtil.getString("elec_policy");
		
		JSONObject json = new ReqStrUtils().getReqString(capChannel, prd, usr, idcard, apply, usrInfo, premium);
		
		HttpConUtils conn = new HttpConUtils();
		String result = conn.doPostJSON(url, json);
		
		logger.info(aid+":电子保单参数:"+json.toJSONString());
		logger.info(aid+":电子保单结果:"+result);
		
		JSONObject rJson = JSONObject.parseObject(result);
		if(rJson.containsKey("status")) {
			int status = rJson.getIntValue("status"); 
			if(status == 3) {
				return false;
			}else {
				return true;
			}
		}else {
			JSONObject data = rJson.getJSONObject("data");
			if(data == null || data.isEmpty()) {
				return false;
			}
			String status = data.getString("status");
			if("0".equals(status)) {
				return true;
			}
			return false;
		}
	}
}
