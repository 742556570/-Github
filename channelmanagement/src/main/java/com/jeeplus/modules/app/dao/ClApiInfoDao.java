package com.jeeplus.modules.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeeplus.common.persistence.CrudDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.app.entity.ClApiInfo;
import com.jeeplus.modules.app.entity.ClLiveFailCnt;

@MyBatisDao
public interface ClApiInfoDao extends CrudDao<ClApiInfo> {
	
	public List<ClApiInfo> getByUsrCode(String USR_CDE);
	
	public int cntByUsrApiNameDT(HashMap<String, Object> params);
	
	public int cntByUsrApiName(HashMap<String, Object> params);
	
	public int cntByTelApiNameDT(HashMap<String, Object> params);
	
	public int cntByTelApiName(HashMap<String, Object> params);
	
	public List<ClLiveFailCnt> getLiveFieldTypeAndCnt(String usrCde);
	
	public List<ClApiInfo> findDxCompensateFail();

}
