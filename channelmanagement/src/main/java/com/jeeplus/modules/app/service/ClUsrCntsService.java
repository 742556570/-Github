package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.app.api.sms.SmsUtil;
import com.jeeplus.modules.app.constant.AppCommonConstants;
import com.jeeplus.modules.app.dao.ClUsrCntsDao;
import com.jeeplus.modules.app.dao.ClUsrDao;
import com.jeeplus.modules.app.entity.ClUsr;
import com.jeeplus.modules.app.entity.ClUsrCnts;
import com.jeeplus.modules.app.utils.AppDateUtils;
import com.jeeplus.modules.app.utils.CacheHelper;
import com.jeeplus.modules.app.utils.GenToken;

@Service
@Transactional(readOnly = false)
public class ClUsrCntsService extends CrudService<ClUsrCntsDao, ClUsrCnts> {

	@Autowired
	private ClUsrCntsDao clUsrCntsDao;

	public boolean chkUsrCntsMdn(String usrCde, String usrRelMob) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("USR_CDE", usrCde);
		params.put("REL_MOBILE", usrRelMob);
		int i = clUsrCntsDao.cntUsrRelMob(params);
		if (i == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addCusrCnts(List<ClUsrCnts> list) {
		int i = 0;
		for (ClUsrCnts clUsrCnts : list) {
			i += clUsrCntsDao.insert(clUsrCnts);
		}
		if(i == 2) {
			return true;
		}else {
			return false;
		}
	}

	public JsonObject getUsrCntsJsonObj(String usrCde) {
		List<ClUsrCnts> list = clUsrCntsDao.getByUsrCodeDisplay(usrCde);
		JsonObject jsObj = new JsonObject();

		for (ClUsrCnts clUsrCnts : list) {
			if (clUsrCnts.getLVL().equals("first")) {
				jsObj.addProperty("relationName", clUsrCnts.getREL_NAME());
				jsObj.addProperty("relationPhone", clUsrCnts.getREL_MOBILE());
				jsObj.addProperty("relation", clUsrCnts.getREL_RELATION());
			} else if (clUsrCnts.getLVL().equals("second")) {
				jsObj.addProperty("otherName", clUsrCnts.getREL_NAME());
				jsObj.addProperty("otherPhone", clUsrCnts.getREL_MOBILE());
				jsObj.addProperty("otherRelation", clUsrCnts.getREL_RELATION());
			}
		}

		return jsObj;
	}

}
