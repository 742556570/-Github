package com.jeeplus.modules.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.dao.ClCrdtExtDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClCrdtExt;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.utils.CacheHelper;

@Service
@Transactional(readOnly = false)
public class ClUsrStsMdfByGonganService {
	private final static Logger logger = LoggerFactory.getLogger(ClUsrStsMdfByGonganService.class);

	@Autowired
	private ClUsrDao usrDao;
	
	@Autowired
	private ClCrdtExtDao crdtDao;
	
	public void getGonganSts(ClUsr clUsr ,JSONObject wdData) {
		JSONObject police_blacklist = wdData.getJSONObject("police_blacklist");
		String IsGonganPoor = "";
		String GonganPoor_Type = "";
		
		if(police_blacklist != null && !police_blacklist.isEmpty()) {
			IsGonganPoor = StringUtils.isEmpty(police_blacklist.getString("IsGonganPoor"))? "":police_blacklist.getString("IsGonganPoor");
			GonganPoor_Type = StringUtils.isEmpty(police_blacklist.getString("GonganPoor_Type"))? "":police_blacklist.getString("GonganPoor_Type");
		}
		
		logger.info("用户:"+clUsr.getUSR_CDE()+",公安状态,IsGonganPoor="+IsGonganPoor+",GonganPoor_Type="+GonganPoor_Type);
		
		if("true".equals(IsGonganPoor)) {
			//修改用户数据库状态
			GonganPoor_Type = GonganPoor_Type.replace(";", "");
			String status = "gonganPoor"+"_"+GonganPoor_Type;
			String token = clUsr.getUSR_TOKEN();
			clUsr.setUSR_STS("-1");
			clUsr.setUSR_GRD(status);
			
			usrDao.updateUsrGrd(clUsr);
			
			//修改用户缓存状态
			CacheHelper.setObject(AppCommonConstants.getValStr("CACHESCHEMA"),
					token + AppCommonConstants.getValStr("CACHETYPESUFFIX_LOGIN"), clUsr,
					AppCommonConstants.getValInt("CACHELOGINTTL"));
			
			//修改授信
//			ClCrdtExt crdtExt = crdtDao.getClCrdtExtByUsrCde(clUsr.getUSR_CDE());
//			crdtExt.setCRDTEXT_TYPE(status);
//			crdtDao.updateByAppSeq(crdtExt);
			
			
		}
	}
	

}
