package com.jeeplus.modules.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeeplus.common.service.CrudService;
import com.jeeplus.modules.app.dao.ClUsrInfoDao;
import com.jeeplus.modules.app.entity.ClUsrInfo;

@Service
@Transactional(readOnly = false)
public class ClUsrInfoService extends CrudService<ClUsrInfoDao, ClUsrInfo> {

	@Autowired
	private ClUsrInfoDao clUsrInfoDao;

	public boolean addUsrInfo(ClUsrInfo clUsrInfo) {

		int i = clUsrInfoDao.insert(clUsrInfo);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isUsrInfoExists(String usrCde) {
		int i = clUsrInfoDao.cntByUsrCode(usrCde);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public ClUsrInfo getUsrInfo(String usrCde) {
		ClUsrInfo usrInfo = clUsrInfoDao.getByUsrCode(usrCde);
		
		return usrInfo;
	}
	
	public int updateUsrInfo(ClUsrInfo clUsrInfo) {

		int i = clUsrInfoDao.updateByusrcde(clUsrInfo);
		
		return i;
		
	}
	
	
}
