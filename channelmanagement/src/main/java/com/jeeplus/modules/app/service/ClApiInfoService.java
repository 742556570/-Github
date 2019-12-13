package com.jeeplus.modules.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClApiInfoDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClLiveFailCnt;
import com.jeeplus.modules.app.utils.TTLUtils;

@Service
@Transactional(readOnly = false)
public class ClApiInfoService extends CrudService<ClApiInfoDao,ClApiInfo>{

	@Autowired
	private ClApiInfoDao apiDao;
	
	
	
	public int getInvokeCntTodayByApiNameAndUsrCode(String apiName,String usrCde) {
		String todayStart = TTLUtils.todayStart();
		String todayEnd = TTLUtils.todayEnd();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_CDE", usrCde);
		params.put("SERVICE_NAME", apiName);
		params.put("START_DT", todayStart);
		params.put("END_DT", todayEnd);
		int i = apiDao.cntByUsrApiNameDT(params);
		return i;
	}
	
	public int getInvokeCntWeekByApiNameAndUsrCode(String apiName,String usrCde) {
		String weekStart = TTLUtils.thisWeekStart();
		String weekyEnd = TTLUtils.thisWeekEnd();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_CDE", usrCde);
		params.put("SERVICE_NAME", apiName);
		params.put("START_DT", weekStart);
		params.put("END_DT", weekyEnd);
		int i = apiDao.cntByUsrApiNameDT(params);
		return i;
	}
	
	public int getInvokeCntByApiNameAndUsrTel(String apiName,String usrTel) {
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_TEL", usrTel);
		params.put("SERVICE_NAME", apiName);
		int i = apiDao.cntByTelApiName(params);
		return i;
	}
	
	public int getInvokeCntTodayByApiNameAndUsrTel(String apiName,String usrTel) {
		String todayStart = TTLUtils.todayStart();
		String todayEnd = TTLUtils.todayEnd();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_TEL", usrTel);
		params.put("SERVICE_NAME", apiName);
		params.put("START_DT", todayStart);
		params.put("END_DT", todayEnd);
		int i = apiDao.cntByTelApiNameDT(params);
		return i;
	}
	
	public int getInvokeCntWeekByApiNameAndUsrTel(String apiName,String usrTel) {
		String weekStart = TTLUtils.thisWeekStart();
		String weekyEnd = TTLUtils.thisWeekEnd();
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_CDE", usrTel);
		params.put("SERVICE_NAME", apiName);
		params.put("START_DT", weekStart);
		params.put("END_DT", weekyEnd);
		int i = apiDao.cntByUsrApiNameDT(params);
		return i;
	}
	
	public int getInvokeCntByApiNameAndUsrCode(String apiName,String usrCde) {
		HashMap<String, Object> params = new HashMap<String,Object>();
		params.put("USR_CDE", usrCde);
		params.put("SERVICE_NAME", apiName);
		int i = apiDao.cntByUsrApiName(params);
		return i;
	}
	
	
	public int addInvokeApiInfo(ClApiInfo clApiInfo) {
		int i = apiDao.insert(clApiInfo);
		return i;
	}
	
	public Map<String, String> getLiveFieldTypeAndCnt(String usrCde){
		List<ClLiveFailCnt> cnts = apiDao.getLiveFieldTypeAndCnt(usrCde);
		Map<String, String> map = new HashMap<String,String>();
		for (ClLiveFailCnt clLiveFailCnt : cnts) {
			String key = clLiveFailCnt.getRESULT();
			String val = clLiveFailCnt.getCNT();
			if(key != null) {
				map.put(key, val);
			}
		}
		if(!map.containsKey("id_attacked")) {
			map.put("id_attacked", "0");
		}
		if(!map.containsKey("mask")) {
			map.put("mask", "0");
		}
		if(!map.containsKey("screen_replay")) {
			map.put("screen_replay", "0");
		}
		if(!map.containsKey("synthetic_face")) {
			map.put("synthetic_face", "0");
		}
		if(!map.containsKey("face_replaced")) {
			map.put("face_replaced", "0");
		}
		if(!map.containsKey("not_self")) {
			map.put("not_self", "0");
		}
		return map;
	}
	
	public List<ClApiInfo> findDxCompensateFail() {
		List<ClApiInfo> list = apiDao.findDxCompensateFail();
		return list;
	}

}
